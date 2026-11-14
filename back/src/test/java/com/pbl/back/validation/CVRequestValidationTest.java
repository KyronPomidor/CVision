package com.pbl.back.validation;

import com.pbl.back.dto.cv.CVRequest;
import org.junit.jupiter.api.Test;

class CVRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesFileMetadataAndExtractedTextLength() {
        CVRequest request = new CVRequest();
        assertInvalid(request, "fileName", "required");
        assertInvalid(request, "filePath", "required");
        request.setFileName("cv.pdf");
        request.setFilePath("uploads/cv.pdf");
        request.setExtractedText("x".repeat(50001));
        assertInvalid(request, "extractedText", "50000");
        request.setExtractedText("");
        assertValid(request);

        request.setFileName("x".repeat(256));
        assertInvalid(request, "fileName", "255");
        request.setFileName("cv.pdf");
        request.setFilePath("x".repeat(1025));
        assertInvalid(request, "filePath", "1024");
    }
}
