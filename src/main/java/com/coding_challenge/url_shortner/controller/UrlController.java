package com.coding_challenge.url_shortner.controller;

import com.coding_challenge.url_shortner.dto.ShortenUrlCreateRequest;
import com.coding_challenge.url_shortner.dto.ShortenUrlResponse;
import com.coding_challenge.url_shortner.model.UrlMapping;
import com.coding_challenge.url_shortner.service.UrlService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@RestController
@AllArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);


    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> createShortUrl(@Valid @RequestBody ShortenUrlCreateRequest request) {
        return ResponseEntity.ok(urlService.createOrGetShortUrl(request));
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirectToOriginal(@PathVariable String shortCode) {
        try{
            ShortenUrlResponse response = urlService.getByShortenUrlCode(shortCode);
            return new RedirectView(response.getOriginalUrl());
        } catch (RuntimeException e){
            logger.warn("Short code not found or failed lookup: {} \n{}", shortCode, e.getMessage());
            return new RedirectView("/error/invalid-shortcode");
        }
    }

    @GetMapping("/error/invalid-shortcode")
    public String showInvalidShortcodePage() {
        return "The short URL you requested does not exist or has expired.";
    }

    // just for testing purpose
    @GetMapping("/url-stats")
    public List<UrlMapping> getUrlStats(){
        return urlService.getUrlStats();
    }
}
