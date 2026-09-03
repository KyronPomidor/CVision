package com.pbl.back.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "profile_skills",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"profile_id", "skill_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ProfileSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private CandidateProfile profile;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    private String level;

    private Integer yearsOfExperience;
}