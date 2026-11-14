package com.pbl.back.validation;

import com.pbl.back.dto.company.CompanyDetailsRequest;
import com.pbl.back.dto.company.CompanyRequest;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;

class CompanyRequestValidationTest extends ValidationTestSupport {

    @Test
    void validatesCompanyFieldsAndNestedDetails() {
        CompanyRequest request = validRequest();
        request.setName("A");
        assertInvalid(request, "name", "2-100");
        request.setName("AB");
        assertValid(request);

        request.setDescription("x".repeat(5001));
        assertInvalid(request, "description", "5000");
        request.setDescription("");
        request.setLocation("x".repeat(256));
        assertInvalid(request, "location", "255");
        request.setLocation("");
        request.setLogo("x".repeat(1025));
        assertInvalid(request, "logo", "1024");
        request.setLogo("");
        request.setPhotos(java.util.Collections.nCopies(21, "photo"));
        assertInvalid(request, "photos", "20");
        request.setPhotos(List.of("x".repeat(1025)));
        assertInvalid(request, "photos", "1024");
        request.setPhotos(List.of());
        request.setDetails(null);
        assertInvalid(request, "details", "required");

        request.setDetails(new CompanyDetailsRequest());
        request.getDetails().setWebsite("ftp://x");
        assertInvalid(request, "details.website", "http:// or https://");
        request.getDetails().setWebsite("");
        request.getDetails().setEmail("");
        assertValid(request);
        request.getDetails().setEmail("a@b");
        assertInvalid(request, "details.email", "valid email");
        request.getDetails().setEmail("");

        request.getDetails().setSize(0);
        assertInvalid(request, "details.size", "greater than 0");
        request.getDetails().setSize(1000001);
        assertInvalid(request, "details.size", "1000000");
        request.getDetails().setSize(10);
        request.getDetails().setFounded(1799);
        assertInvalid(request, "details.founded", "1800");
        request.getDetails().setFounded(Year.now().getValue() + 1);
        assertInvalid(request, "details.foundedNotInFuture", "cannot be in the future");
        request.getDetails().setFounded(Year.now().getValue());
        request.getDetails().setLinkedin("ftp://x");
        assertInvalid(request, "details.linkedin", "http:// or https://");
        request.getDetails().setLinkedin("");
        request.getDetails().setInstagram("ftp://x");
        assertInvalid(request, "details.instagram", "http:// or https://");
        request.getDetails().setInstagram("");
        request.getDetails().setTwitter("ftp://x");
        assertInvalid(request, "details.twitter", "http:// or https://");
        request.getDetails().setTwitter("");
        request.getDetails().setFacebook("ftp://x");
        assertInvalid(request, "details.facebook", "http:// or https://");
        request.getDetails().setFacebook("");
        request.getDetails().setAddress("x".repeat(256));
        assertInvalid(request, "details.address", "255");
        request.getDetails().setAddress("");
        assertValid(request);
    }

    private CompanyRequest validRequest() {
        CompanyRequest request = new CompanyRequest();
        request.setName("AB");
        request.setDetails(new CompanyDetailsRequest());
        return request;
    }
}
