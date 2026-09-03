package com.pbl.back.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_recommendations")
@Getter
@Setter
@NoArgsConstructor
public class JobRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @Column(name = "match_score")
    private Double matchScore;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
