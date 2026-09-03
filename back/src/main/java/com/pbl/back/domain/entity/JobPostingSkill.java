package com.pbl.back.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "job_posting_skills",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"job_posting_id", "skill_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class JobPostingSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}