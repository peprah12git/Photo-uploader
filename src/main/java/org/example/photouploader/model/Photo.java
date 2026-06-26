package org.example.photouploader.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String s3Key;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String cloudFrontUrl;

    @Column(nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();
}