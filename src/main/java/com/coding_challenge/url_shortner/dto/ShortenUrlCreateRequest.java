package com.coding_challenge.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.URL;


@Builder
@Data
@AllArgsConstructor
public class ShortenUrlCreateRequest {

    @NotBlank(message = "Original URL must not be blank")
    @URL(message = "Original URL must be a valid URL")
    private String originalUrl;

}
