package com.pbl.back.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "cv_skills",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cv_id", "skill_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class CVSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "confidence_score")
    private Double confidenceScore;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;
}