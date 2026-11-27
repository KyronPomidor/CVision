package com.pbl.back.service;

import com.pbl.back.dto.skill.ExtractedSkill;

import com.pbl.back.domain.entity.CV;

import java.util.List;

public interface CvSkillSyncService {
    CV saveCvWithSkills(CV cv, List<String> skillNames);

    CV replaceExtractedSkills(CV cv, List<ExtractedSkill> skills);

    CV replaceCvWithSkills(CV cv, List<String> skillNames);
}
