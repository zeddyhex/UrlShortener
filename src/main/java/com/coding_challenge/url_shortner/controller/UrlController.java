package com.coding_challenge.url_shortner.controller;

import com.coding_challenge.url_shortner.dto.ShortenUrlCreateRequest;
import com.coding_challenge.url_shortner.dto.ShortenUrlResponse;
import com.coding_challenge.url_shortner.model.UrlMapping;
import com.coding_challenge.url_shortner.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@AllArgsConstructor
public class UrlController {

    public final UrlService urlService;

    @GetMapping("/url-stats")
    public List<UrlMapping> getUrlStats(){
        return urlService.getUrlStats();
    }

    @PostMapping("/shorten")
    @ResponseBody
    public ResponseEntity<ShortenUrlResponse> createShortUrl(@RequestBody @Validated ShortenUrlCreateRequest request) {
        UrlMapping urlMapping = urlService.createOrGetShortUrl(request);
        return ResponseEntity.ok(urlService.mapEntityToDto(urlMapping));
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirectToOriginal(@PathVariable String shortCode) {
        UrlMapping urlMapping = urlService.getByShortenUrlCode(shortCode);
        return new RedirectView(urlMapping.getOriginalUrl());
    }

}
