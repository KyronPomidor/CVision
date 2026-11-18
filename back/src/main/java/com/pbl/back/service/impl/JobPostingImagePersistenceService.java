package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.entity.JobPostingImage;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.repository.JobPostingImageRepository;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.service.ImageStorageService.StoredImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingImagePersistenceService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingImageRepository jobPostingImageRepository;

    @Transactional
    public List<JobPostingImage> saveImages(Long postingId, List<StoredImage> images) {
        JobPosting posting = jobPostingRepository.findById(postingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobPosting with id '" + postingId + "' not found."));
        List<JobPostingImage> existingImages =
                jobPostingImageRepository.findByJobPostingIdOrderBySortOrderAsc(postingId);
        int nextSortOrder = existingImages.stream()
                .mapToInt(JobPostingImage::getSortOrder)
                .max()
                .orElse(0) + 1;
        LocalDateTime uploadedAt = LocalDateTime.now();
        List<JobPostingImage> rows = new ArrayList<>();

        for (StoredImage image : images) {
            rows.add(JobPostingImage.builder()
                    .jobPosting(posting)
                    .filePath(image.relativePath())
                    .originalFileName(image.originalFileName())
                    .contentType(image.contentType())
                    .sortOrder(nextSortOrder++)
                    .uploadedAt(uploadedAt)
                    .build());
        }

        return jobPostingImageRepository.saveAll(rows);
    }
}
