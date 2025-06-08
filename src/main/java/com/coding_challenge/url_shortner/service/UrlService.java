package com.coding_challenge.url_shortner.service;

import com.coding_challenge.url_shortner.dto.ShortenUrlCreateRequest;
import com.coding_challenge.url_shortner.dto.ShortenUrlResponse;
import com.coding_challenge.url_shortner.model.UrlMapping;
import com.coding_challenge.url_shortner.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {
    @Value("${homeURL}")
    private String homeBaseURL;

    public final UrlMappingRepository urlMappingRepository;

    public List<UrlMapping> getUrlStats(){
        return urlMappingRepository.findAll();
    }

    public ShortenUrlResponse createOrGetShortUrl(ShortenUrlCreateRequest request) {
        String originalUrl = request.getOriginalUrl();
        Optional<UrlMapping> existing = urlMappingRepository.findByOriginalUrl(originalUrl);
        if(existing.isPresent()){
            UrlMapping urlMapping = existing.get();
            urlMapping.incrementCreationRequestCount();
            return mapEntityToDto(urlMappingRepository.save(urlMapping));
        }

        String shortCode = generateUniqueShortCode();
        UrlMapping newUrlMapping = new UrlMapping();
        newUrlMapping.setOriginalUrl(originalUrl);
        newUrlMapping.setShortenUrlCode(shortCode);
        return mapEntityToDto(urlMappingRepository.save(newUrlMapping));
    }

    public ShortenUrlResponse getByShortenUrlCode(String shortCode) {
        UrlMapping urlMapping = urlMappingRepository.findByShortenUrlCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        urlMapping.incrementVisitCount();
        return mapEntityToDto(urlMappingRepository.save(urlMapping));
    }

    private String generateUniqueShortCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8);
        } while (urlMappingRepository.findByShortenUrlCode(code).isPresent());
        return code;
    }

    private ShortenUrlResponse mapEntityToDto(UrlMapping urlMapping){
        // for getting homeBaseUrl from Context
        // String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        return ShortenUrlResponse.builder()
                .originalUrl(urlMapping.getOriginalUrl())
                .shortUrl(homeBaseURL + "/" + urlMapping.getShortenUrlCode())
                .visitCount(urlMapping.getVisitCount())
                .createRequestCount(urlMapping.getCreateRequestCount())
                .build();
    }
}
