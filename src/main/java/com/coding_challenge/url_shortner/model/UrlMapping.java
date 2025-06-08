package com.coding_challenge.url_shortner.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder (toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (unique = true, nullable = false)
    private String originalUrl;

    @Column (unique = true, nullable = false)
    private String shortenUrlCode;

    @Column
    private int visitCount = 0;

    @Column
    private int createRequestCount = 1;

    public void incrementCreationRequestCount(){
        createRequestCount++;
    }

    public void incrementVisitCount(){
        visitCount++;
    }
}
