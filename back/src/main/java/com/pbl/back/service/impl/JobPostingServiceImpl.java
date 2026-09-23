package com.pbl.back.service.impl;



import com.pbl.back.domain.entity.*;
import com.pbl.back.domain.enums.JobStatus;
import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.exception.ResourceNotFoundException;
import com.pbl.back.mapper.JobPostingMapper;
import com.pbl.back.mapper.JobPostingSkillMapper;
import com.pbl.back.repository.*;
import com.pbl.back.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    @Override
    public JobPostingResponse create(Long companyId, JobPostingRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company with id '" + companyId + "' not found."));

        JobPosting jobPosting = mapper.toEntity(request);

        jobPosting.setCompany(company);

        JobPosting savedJob = repository.save(jobPosting);

        if (request.getSkillIds() != null) {

            for (Long skillId : request.getSkillIds()) {

                Skill skill = skillRepository.findById(skillId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Skill with id '" + skillId + "' not found."));

                JobPostingSkill mapping = JobPostingSkill.builder()
                                .jobPosting(savedJob)
                                .skill(skill)
                                .build();

                jobPostingSkillRepository.save(mapping);
            }
        }
        return getById(savedJob.getId());
    }

    @Override
    public List<JobPostingResponse> getByCompanyId(Long companyId) {
        List<JobPosting> postings = repository.findByCompanyId(companyId);

        List<JobPostingResponse> postingResponses = new ArrayList<>();
        postings.forEach(posting -> {

                    List<SkillResponse> skills = jobPostingSkillMapper.toSkillResponse(
                            jobPostingSkillRepository.findByJobPostingId(posting.getId()));

                    postingResponses.add(mapper.toResponse(posting, skills));
                });
        return postingResponses;
    }

    @Override
    public List<JobPostingResponse> getMatchesForUser(Long userId) {
        Set<Long> userSkillIds = candidateProfileRepository.findByUserId(userId)
                .map(CandidateProfile::getId)
                .map(profileSkillRepository::findByProfileId)
                .orElseGet(List::of)
                .stream()
                .map(profileSkill -> profileSkill.getSkill().getId())
                .collect(Collectors.toSet());

        return repository.findByStatus(JobStatus.OPEN).stream()
                .map(posting -> toMatchResponse(posting, userSkillIds))
                .sorted(Comparator.comparing(JobPostingResponse::getMatchScore).reversed())
                .toList();
    }

    private JobPostingResponse toMatchResponse(JobPosting posting, Set<Long> userSkillIds) {
        List<JobPostingSkill> requiredSkills = jobPostingSkillRepository.findByJobPostingId(posting.getId());
        Set<Long> requiredSkillIds = requiredSkills.stream()
                .map(jobPostingSkill -> jobPostingSkill.getSkill().getId())
                .collect(Collectors.toCollection(HashSet::new));

        long matchedSkills = requiredSkillIds.stream()
                .filter(userSkillIds::contains)
                .count();
        double matchScore = requiredSkillIds.isEmpty()
                ? 0.0
                : (matchedSkills * 100.0) / requiredSkillIds.size();

        return mapper.toResponse(posting,
                jobPostingSkillMapper.toSkillResponse(requiredSkills),
                matchScore);
    }

    @Override
    public JobPostingResponse getById(Long id) {
        JobPosting posting = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPosting with id '" + id + "' not found."));

        List<SkillResponse> skills = jobPostingSkillMapper.toSkillResponse(
                jobPostingSkillRepository.findByJobPostingId(id)
        );

        return mapper.toResponse(posting, skills);
    }

    @Override
    public JobPostingResponse update(Long id, JobPostingRequest request) {
        JobPosting posting = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPosting with id '" + id + "' not found."));

        posting.setTitle(request.getTitle());
        posting.setDescription(request.getDescription());
        posting.setLocation(request.getLocation());
        posting.setEmploymentType(request.getEmploymentType());
        posting.setSalary(request.getSalary());

        if (!request.getSkillIds().isEmpty()) {

            jobPostingSkillRepository.deleteAll(jobPostingSkillRepository.findByJobPostingId(id));

            for (Long skillId : request.getSkillIds()) {

                Skill skill = skillRepository.findById(skillId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Skill with id '" + skillId + "' not found."));

                JobPostingSkill mapping = JobPostingSkill.builder()
                        .jobPosting(posting)
                        .skill(skill)
                        .build();

                jobPostingSkillRepository.save(mapping);
            }
        }

        return getById(posting.getId());
    }

    @Override
    public void delete(Long id) {
        JobPosting posting = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPosting with id '" + id + "' not found."));

        jobPostingSkillRepository.deleteAll(jobPostingSkillRepository.findByJobPostingId(id));

        repository.delete(posting);
    }
}
