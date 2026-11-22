package com.pbl.back.service;

import com.pbl.back.dto.profileskill.ProfileSkillRequest;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;

import java.util.List;

public interface ProfileSkillService {

    ProfileSkillResponse addSkill(ProfileSkillRequest request);

    List<ProfileSkillResponse> getSkills();

    ProfileSkillResponse getById(Long id);

    ProfileSkillResponse update(Long id, ProfileSkillRequest request);

    void delete(Long id);
}