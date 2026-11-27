package com.pbl.back.service.impl;



import com.pbl.back.domain.entity.*;
import com.pbl.back.domain.enums.JobStatus;
import com.pbl.back.exception.FileStorageException;
import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.JobPostingMapper;
import com.pbl.back.mapper.JobPostingSkillMapper;
import com.pbl.back.repository.*;
import com.pbl.back.service.JobPostingService;
import com.pbl.back.service.CurrentUserService;
import com.pbl.back.service.ImageStorageService;
import com.pbl.back.service.ImageStorageService.StoredImage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository repository;
    private final JobPostingMapper mapper;
    private final CompanyRepository companyRepository;
    private final JobPostingSkillRepository jobPostingSkillRepository;
    private final SkillRepository skillRepository;
    private final JobPostingSkillMapper jobPostingSkillMapper;
    private final CandidateProfileRepository candidateProfileRepository;
    private final ProfileSkillRepository profileSkillRepository;
    private final CurrentUserService currentUserService;
    private final JobPostingImageRepository jobPostingImageRepository;
    private final ImageStorageService imageStorageService;
    private final JobPostingImagePersistenceService imagePersistenceService;

    private static final int MAX_IMAGES_PER_POSTING = 10;


    @Override
    @Transactional
    public JobPostingResponse createForCurrentCompany(JobPostingRequest request) {
        Company company = companyRepository.findByUserId(currentUserService.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Create your company profile first."));
        assertCompanyOwner(company);

        JobPosting jobPosting = mapper.toEntity(request);

        jobPosting.setCompany(company);

        JobPosting savedJob = repository.save(jobPosting);

        saveRequirements(savedJob, request);
        return getById(savedJob.getId());
    }

    @Override
    public List<JobPostingResponse> getForCurrentCompany() {
        Company company = companyRepository.findByUserId(currentUserService.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Create your company profile first."));
        List<JobPosting> postings = repository.findByCompanyId(company.getId());

        List<JobPostingResponse> postingResponses = new ArrayList<>();
        postings.forEach(posting -> {

            List<SkillResponse> skills = jobPostingSkillMapper.toSkillResponse(
                    jobPostingSkillRepository.findByJobPostingId(posting.getId()));

            postingResponses.add(mapper.toResponse(posting, skills,
                    jobPostingImageRepository.findByJobPostingIdOrderBySortOrderAsc(posting.getId())));
        });
        return postingResponses;
    }

    @Override
    public List<JobPostingResponse> getMatchesForUser(Long userId) {
        Map<Long, Integer> experienceBySkill = candidateProfileRepository.findByUserId(userId)
                .map(CandidateProfile::getId)
                .map(profileSkillRepository::findByProfileId)
                .orElseGet(List::of)
                .stream()
                .collect(Collectors.toMap(link -> link.getSkill().getId(),
                        link -> Objects.requireNonNullElse(link.getYearsOfExperience(), 0), Math::max));

        return repository.findByStatus(JobStatus.OPEN).stream()
                .map(posting -> toMatchResponse(posting, experienceBySkill))
                .filter(posting -> posting.getMatchScore() > 0)
                .sorted(Comparator.comparing(JobPostingResponse::getMatchScore).reversed())
                .toList();
    }

    private JobPostingResponse toMatchResponse(JobPosting posting, Map<Long, Integer> experienceBySkill) {
        List<JobPostingSkill> requiredSkills = jobPostingSkillRepository.findByJobPostingId(posting.getId());
        long matchedSkills = requiredSkills.stream()
                .filter(link -> experienceBySkill.getOrDefault(link.getSkill().getId(), -1)
                        >= Objects.requireNonNullElse(link.getRequiredYears(), 0))
                .count();
        double matchScore = requiredSkills.isEmpty() ? 0.0 : matchedSkills * 100.0 / requiredSkills.size();
        return mapper.toResponse(posting,
                jobPostingSkillMapper.toSkillResponse(requiredSkills),
                jobPostingImageRepository.findByJobPostingIdOrderBySortOrderAsc(posting.getId()),
                matchScore);
    }

    @Override
    public JobPostingResponse getById(Long id) {
        JobPosting posting = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPosting with id '" + id + "' not found."));

        List<SkillResponse> skills = jobPostingSkillMapper.toSkillResponse(
                jobPostingSkillRepository.findByJobPostingId(id)
        );

        return mapper.toResponse(posting, skills,
                jobPostingImageRepository.findByJobPostingIdOrderBySortOrderAsc(posting.getId()));
    }

    @Override
    @Transactional
    public JobPostingResponse update(Long id, JobPostingRequest request) {
        JobPosting posting = getOwnedPosting(id);

        posting.setTitle(request.getTitle());
        posting.setDescription(request.getDescription());

        posting.getDetails().setJobType(request.getDetails().getJobType());
        posting.getDetails().setWorkSetting(request.getDetails().getWorkSetting());
        posting.getDetails().setLocation(request.getDetails().getLocation());
        posting.getDetails().setSalary(request.getDetails().getSalary());
        posting.getDetails().setSchedule(request.getDetails().getSchedule());
        posting.getDetails().setExperience(request.getDetails().getExperience());
        posting.getDetails().setEducation(request.getDetails().getEducation());
        posting.getDetails().setContactEmail(request.getDetails().getContactEmail());

        saveRequirements(posting, request);
        return getById(posting.getId());
    }

    @Override
    @Transactional
    public JobPostingResponse updateRequiredYears(Long postingId, Long skillId, int years) {
        getOwnedPosting(postingId);
        JobPostingSkill link = jobPostingSkillRepository.findByJobPostingIdAndSkillId(postingId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found on this posting."));
        link.setRequiredYears(years);
        return getById(postingId);
    }

    private void saveRequirements(JobPosting posting, JobPostingRequest request) {
        Map<Long, Integer> years = request.getRequiredYearsBySkill();
        if (years == null && request.getSkillIds().isEmpty()) return;
        Set<Long> desired = years == null ? new LinkedHashSet<>(request.getSkillIds()) : years.keySet();
        List<JobPostingSkill> existing = jobPostingSkillRepository.findByJobPostingId(posting.getId());
        Set<Long> retained = new HashSet<>();
        for (JobPostingSkill link : existing) {
            Long id = link.getSkill().getId();
            if (desired.contains(id)) {
                retained.add(id);
                if (years != null) link.setRequiredYears(years.get(id));
            } else {
                jobPostingSkillRepository.delete(link);
            }
        }
        for (Long id : desired) {
            if (retained.contains(id)) continue;
            Skill skill = skillRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Skill with id '" + id + "' not found."));
            int minimum = years == null ? Objects.requireNonNullElse(request.getDetails().getExperience(), 0) : years.get(id);
            jobPostingSkillRepository.save(JobPostingSkill.builder()
                    .jobPosting(posting).skill(skill).requiredYears(minimum).build());
        }
    }
    @Override
    @Transactional
    public void delete(Long id) {
        JobPosting posting = getOwnedPosting(id);

        jobPostingSkillRepository.deleteAll(jobPostingSkillRepository.findByJobPostingId(id));
        jobPostingImageRepository.deleteAll(
                jobPostingImageRepository.findByJobPostingIdOrderBySortOrderAsc(id));

        repository.delete(posting);
        afterCommit(() -> imageStorageService.deleteDirectoryQuietly("postings/" + id));
    }

    @Override
    public JobPostingResponse uploadImages(Long postingId, List<MultipartFile> files) {
        getOwnedPosting(postingId);
        if (files == null || files.isEmpty()) {
            throw new FileStorageException("At least one image file is required.");
        }

        long existingCount = jobPostingImageRepository.countByJobPostingId(postingId);
        if (existingCount + files.size() > MAX_IMAGES_PER_POSTING) {
            throw new FileStorageException("A posting may have no more than 10 images.");
        }

        List<StoredImage> storedImages = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                storedImages.add(imageStorageService.store(file, "postings/" + postingId));
            }
            imagePersistenceService.saveImages(postingId, storedImages);
        } catch (RuntimeException ex) {
            storedImages.forEach(image -> imageStorageService.deleteQuietly(image.relativePath()));
            throw ex;
        }
        return getById(postingId);
    }

    @Override
    public ResponseEntity<Resource> getImage(Long postingId, Long imageId) {
        JobPostingImage image = jobPostingImageRepository.findByIdAndJobPostingId(imageId, postingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Image with id '" + imageId + "' not found for posting '" + postingId + "'."));
        return inlineImage(image.getFilePath(), image.getContentType());
    }

    @Override
    @Transactional
    public void deleteImage(Long postingId, Long imageId) {
        getOwnedPosting(postingId);
        JobPostingImage image = jobPostingImageRepository.findByIdAndJobPostingId(imageId, postingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Image with id '" + imageId + "' not found for posting '" + postingId + "'."));
        jobPostingImageRepository.delete(image);
        afterCommit(() -> imageStorageService.deleteQuietly(image.getFilePath()));
    }

    private JobPosting getOwnedPosting(Long id) {
        JobPosting posting = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPosting with id '" + id + "' not found."));
        assertCompanyOwner(posting.getCompany());
        return posting;
    }

    private void assertCompanyOwner(Company company) {
        if (company.getUser() == null
                || !company.getUser().getId().equals(currentUserService.getId())) {
            throw new AccessDeniedException("You do not own this company.");
        }
    }

    private ResponseEntity<Resource> inlineImage(String filePath, String contentType) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline().build().toString())
                .cacheControl(CacheControl.noCache().cachePrivate())
                .body(imageStorageService.load(filePath));
    }

    private void afterCommit(Runnable action) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                action.run();
            }
        });
    }
}
