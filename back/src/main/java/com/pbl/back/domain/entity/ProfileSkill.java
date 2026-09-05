package com.pbl.back.domain.entity;

import com.pbl.back.domain.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.*;

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
@AllArgsConstructor
@Builder
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillLevel level;

    private Integer yearsOfExperience;
}