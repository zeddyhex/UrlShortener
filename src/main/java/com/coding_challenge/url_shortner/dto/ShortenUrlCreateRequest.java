package com.coding_challenge.url_shortner.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortenUrlCreateRequest {
    private String originalUrl;
}
