package com.pbl.back.service.impl;

import com.pbl.back.domain.entity.Company;
import com.pbl.back.domain.entity.JobPosting;
import com.pbl.back.domain.entity.JobPostingSkill;
import com.pbl.back.domain.entity.Skill;
import com.pbl.back.dto.jobposting.JobPostingRequest;
import com.pbl.back.dto.jobposting.JobPostingResponse;
import com.pbl.back.dto.skill.SkillResponse;
import com.pbl.back.mapper.JobPostingMapper;
import com.pbl.back.mapper.JobPostingSkillMapper;
import com.pbl.back.repository.CompanyRepository;
import com.pbl.back.repository.JobPostingRepository;
import com.pbl.back.repository.JobPostingSkillRepository;
import com.pbl.back.repository.SkillRepository;
import com.pbl.back.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository repository;
    private final JobPostingMapper mapper;
    private final CompanyRepository companyRepository;
    private final JobPostingSkillRepository jobPostingSkillRepository;
    private final SkillRepository skillRepository;
    private final JobPostingSkillMapper jobPostingSkillMapper;

    @Override
    public JobPostingResponse create(Long companyId, JobPostingRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow();

        JobPosting jobPosting = mapper.toEntity(request);

        jobPosting.setCompany(company);

        JobPosting savedJob = repository.save(jobPosting);

        if (request.getSkillIds() != null) {

            for (Long skillId : request.getSkillIds()) {

                Skill skill = skillRepository.findById(skillId)
                        .orElseThrow();

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
    public JobPostingResponse getById(Long id) {
        JobPosting posting = repository.findById(id)
                        .orElseThrow();

        List<SkillResponse> skills = jobPostingSkillMapper.toSkillResponse(
                jobPostingSkillRepository.findByJobPostingId(id)
        );

        return mapper.toResponse(posting, skills);
    }

    @Override
    public JobPostingResponse update(Long id, JobPostingRequest request) {
        JobPosting posting = repository.findById(id)
                .orElseThrow();

        posting.setTitle(request.getTitle());
        posting.setDescription(request.getDescription());
        posting.setLocation(request.getLocation());
        posting.setEmploymentType(request.getEmploymentType());
        posting.setSalary(request.getSalary());

        if (!request.getSkillIds().isEmpty()) {

            jobPostingSkillRepository.deleteAll(jobPostingSkillRepository.findByJobPostingId(id));

            for (Long skillId : request.getSkillIds()) {

                Skill skill = skillRepository.findById(skillId)
                        .orElseThrow();

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
                .orElseThrow();

        jobPostingSkillRepository.deleteAll(jobPostingSkillRepository.findByJobPostingId(id));

        repository.delete(posting);
    }
}
