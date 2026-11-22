package com.pbl.back.service;

import com.pbl.back.dto.candidateprofile.CandidateProfileRequest;
import com.pbl.back.dto.candidateprofile.CandidateProfileResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CandidateProfileService {
    CandidateProfileResponse create(CandidateProfileRequest request);

    CandidateProfileResponse getMe();

    public CandidateProfileResponse getById(Long id);

    CandidateProfileResponse updateMe(CandidateProfileRequest request);

    void deleteMe();

    CandidateProfileResponse uploadPicture(MultipartFile file);

    ResponseEntity<Resource> getPicture(Long id);

    void deletePicture();
}
