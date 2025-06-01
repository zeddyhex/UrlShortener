package com.coding_challenge.url_shortner.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShortenUrlCreateRequest {
    private String originalUrl;
}
