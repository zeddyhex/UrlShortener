package com.coding_challenge.url_shortner.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShortenUrlResponse {
    private String originalUrl;
    private String shortUrl;
    private int visitCount;
    private int createRequestCount;
}
