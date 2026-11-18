package com.pbl.back.dto.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDetailsRequest {

    @Size(max = 255, message = "must be at most 255 characters")
    @Pattern(regexp = "^$|^https?://\\S+$",
            message = "must start with http:// or https://")
    private String website;

    @Size(max = 254, message = "must be at most 254 characters")
    @Email(regexp = "^$|^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$",
            message = "must be a valid email address")
    private String email;

    @Size(max = 255, message = "must be at most 255 characters")
    private String address;

    @Positive(message = "must be greater than 0")
    @Max(value = 1000000, message = "must be at most 1000000")
    private Integer size;

    @Min(value = 1800, message = "must be at least 1800")
    private Integer founded;

    @Size(max = 255, message = "must be at most 255 characters")
    @Pattern(regexp = "^$|^https?://\\S+$",
            message = "must start with http:// or https://")
    private String linkedin;

    @Size(max = 255, message = "must be at most 255 characters")
    @Pattern(regexp = "^$|^https?://\\S+$",
            message = "must start with http:// or https://")
    private String instagram;

    @Size(max = 255, message = "must be at most 255 characters")
    @Pattern(regexp = "^$|^https?://\\S+$",
            message = "must start with http:// or https://")
    private String twitter;

    @Size(max = 255, message = "must be at most 255 characters")
    @Pattern(regexp = "^$|^https?://\\S+$",
            message = "must start with http:// or https://")
    private String facebook;

    @JsonIgnore
    @AssertTrue(message = "cannot be in the future")
    public boolean isFoundedNotInFuture() {
        return founded == null || founded <= Year.now().getValue();
    }
}
