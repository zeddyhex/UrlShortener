package com.coding_challenge.url_shortner.service;

import com.coding_challenge.url_shortner.dto.ShortenUrlCreateRequest;
import com.coding_challenge.url_shortner.dto.ShortenUrlResponse;
import com.coding_challenge.url_shortner.model.UrlMapping;
import com.coding_challenge.url_shortner.repository.UrlMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UrlService {
    public final UrlMappingRepository urlMappingRepository;

    public List<UrlMapping> getUrlStats(){
        return urlMappingRepository.findAll();
    }

    public UrlMapping createOrGetShortUrl(ShortenUrlCreateRequest request) {
        String originalUrl = request.getOriginalUrl();
        Optional<UrlMapping> existing = urlMappingRepository.findByOriginalUrl(originalUrl);
        if(existing.isPresent()){
            UrlMapping urlMapping = existing.get();
            urlMapping.setCreateRequestCount(urlMapping.getCreateRequestCount() + 1);
            return urlMappingRepository.save(urlMapping);
        }

        String shortCode = generateUniqueShortCode();
        UrlMapping newUrlMapping = new UrlMapping();
        newUrlMapping.setOriginalUrl(originalUrl);
        newUrlMapping.setShortenUrlCode(shortCode);
        return urlMappingRepository.save(newUrlMapping);
    }

    public UrlMapping getByShortenUrlCode(String shortCode) {
        UrlMapping urlMapping = urlMappingRepository.findByShortenUrlCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        urlMapping.setVisitCount(urlMapping.getVisitCount() + 1);
        return urlMappingRepository.save(urlMapping);
    }

    private String generateUniqueShortCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8);
        } while (urlMappingRepository.findByShortenUrlCode(code).isPresent());
        return code;
    }

    public ShortenUrlResponse mapEntityToDto(UrlMapping urlMapping){
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        return ShortenUrlResponse.builder()
                .originalUrl(urlMapping.getOriginalUrl())
                .shortUrl(homeURL + "/" + urlMapping.getShortenUrlCode())
                .visitCount(urlMapping.getVisitCount())
                .createRequestCount(urlMapping.getCreateRequestCount())
                .build();
    }
}
