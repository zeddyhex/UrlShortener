package com.coding_challenge.url_shortner.repository;

import com.coding_challenge.url_shortner.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);
    Optional<UrlMapping> findByShortenUrlCode(String shortenUrlCode);
}
