package com.pbl.back.service;

import com.pbl.back.dto.profileskill.ProfileSkillRequest;
import com.pbl.back.dto.profileskill.ProfileSkillResponse;

import java.util.List;

public interface ProfileSkillService {

    ProfileSkillResponse addSkill(Long profileId, ProfileSkillRequest request);

    List<ProfileSkillResponse> getSkills(Long profileId);

    ProfileSkillResponse getById(Long id);

    ProfileSkillResponse update(Long id, ProfileSkillRequest request);

    void delete(Long id);
}