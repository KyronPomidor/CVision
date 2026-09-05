package com.pbl.back.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidate_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String name;

    private String location;

    private String education;

    @Column(columnDefinition = "TEXT")
    private String experience;

    @Column(columnDefinition = "TEXT")
    private String description;
}