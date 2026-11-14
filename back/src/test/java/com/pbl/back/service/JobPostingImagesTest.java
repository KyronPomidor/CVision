package com.pbl.back.service;

import com.pbl.back.domain.entity.Company;
import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.entity.JobPostingImage;
import com.pbl.back.domain.entity.User;
import com.pbl.back.exception.FileStorageException;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.JobPostingMapper;
import com.pbl.back.mapper.JobPostingSkillMapper;
import com.pbl.back.repository.CandidateProfileRepository;
import com.pbl.back.repository.CompanyRepository;
import com.pbl.back.repository.JobPostingImageRepository;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.repository.JobPostingSkillRepository;
import com.pbl.back.repository.ProfileSkillRepository;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.ImageStorageService.StoredImage;
import com.pbl.back.service.impl.JobPostingImagePersistenceService;
import com.pbl.back.service.impl.JobPostingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobPostingImagesTest {

    @Mock
    private JobPostingRepository postingRepository;
    @Mock
    private JobPostingMapper postingMapper;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private JobPostingSkillRepository postingSkillRepository;
    @Mock
    private SkillRepository skillRepository;
    @Mock
    private JobPostingSkillMapper postingSkillMapper;
    @Mock
    private CandidateProfileRepository candidateProfileRepository;
    @Mock
    private ProfileSkillRepository profileSkillRepository;
    @Mock
    private CurrentUserService currentUserService;
    @Mock
    private JobPostingImageRepository imageRepository;
    @Mock
    private ImageStorageService imageStorageService;
    @Mock
    private JobPostingImagePersistenceService imagePersistenceService;

    @TempDir
    private Path baseDirectory;

    private JobPostingServiceImpl service;
    private JobPosting posting;

    @BeforeEach
    void setUp() {
        service = newService(imageStorageService);
        posting = JobPosting.builder()
                .id(15L)
                .company(Company.builder().user(User.builder().id(8L).build()).build())
                .build();
    }

    private JobPostingServiceImpl newService(ImageStorageService storage) {
        return new JobPostingServiceImpl(
                postingRepository,
                postingMapper,
                companyRepository,
                postingSkillRepository,
                skillRepository,
                postingSkillMapper,
                candidateProfileRepository,
                profileSkillRepository,
                currentUserService,
                imageRepository,
                storage,
                imagePersistenceService);
    }

    @Test
    void nonOwnerCannotUploadOrDeletePostingImages() {
        when(postingRepository.findById(15L)).thenReturn(Optional.of(posting));
        when(currentUserService.getId()).thenReturn(9L);

        assertThrows(AccessDeniedException.class, () -> service.uploadImages(15L,
                List.of(new MockMultipartFile("files", "image.png", "image/png", new byte[]{1}))));
        assertThrows(AccessDeniedException.class, () -> service.deleteImage(15L, 3L));
        verify(imageStorageService, never()).store(any(), any());
        verify(imageRepository, never()).delete(any(JobPostingImage.class));
    }

    @Test
    void rejectsEleventhImageWithoutStoringFiles() {
        when(postingRepository.findById(15L)).thenReturn(Optional.of(posting));
        when(currentUserService.getId()).thenReturn(8L);
        when(imageRepository.countByJobPostingId(15L)).thenReturn(10L);
        service = newService(new ImageStorageService(baseDirectory.toString()));

        assertThrows(FileStorageException.class, () -> service.uploadImages(15L,
                List.of(new MockMultipartFile("files", "image.png", "image/png", new byte[]{1}))));

        assertFalse(Files.exists(baseDirectory.resolve("postings").resolve("15")));
        verify(imagePersistenceService, never()).saveImages(any(), anyList());
    }

    @Test
    void continuesSortOrderFromExistingImages() {
        JobPostingImage first = JobPostingImage.builder().sortOrder(1).build();
        when(postingRepository.findById(15L)).thenReturn(Optional.of(posting));
        when(imageRepository.findByJobPostingIdOrderBySortOrderAsc(15L))
                .thenReturn(List.of(first));
        when(imageRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        JobPostingImagePersistenceService persistenceService =
                new JobPostingImagePersistenceService(postingRepository, imageRepository);

        List<JobPostingImage> saved = persistenceService.saveImages(15L, List.of(
                new StoredImage("postings/15/a.png", "image/png", "a.png"),
                new StoredImage("postings/15/b.png", "image/png", "b.png")));

        assertEquals(List.of(1, 2, 3), java.util.stream.Stream.concat(
                List.of(first).stream(), saved.stream()).map(JobPostingImage::getSortOrder).toList());
        assertNotNull(saved.get(0).getUploadedAt());
        verify(imageRepository).saveAll(anyList());
    }

    @Test
    void imageFromAnotherPostingIsNotFound() {
        when(imageRepository.findByIdAndJobPostingId(3L, 15L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getImage(15L, 3L));
    }
}
