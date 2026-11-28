package com.pbl.back.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostingSkillYearsBackfill implements CommandLineRunner {
    private final JdbcTemplate jdbc;

    @Override
    public void run(String... args) {
        // Preserve the effective match threshold of links created before required_years existed.
        boolean legacyYears = Boolean.TRUE.equals(jdbc.queryForObject("""
                SELECT COUNT(*) > 0 FROM INFORMATION_SCHEMA.COLUMNS
                WHERE TABLE_SCHEMA = CURRENT_SCHEMA AND LOWER(TABLE_NAME) = 'skills'
                  AND LOWER(COLUMN_NAME) = 'years_of_experience'
                """, Boolean.class));
        String skillYears = legacyYears
                ? "COALESCE((SELECT s.years_of_experience FROM skills s WHERE s.id = skill_id), 0)"
                : "0";
        jdbc.update("""
                UPDATE job_posting_skills SET required_years = GREATEST(
                    COALESCE((SELECT p.experience FROM job_postings p WHERE p.id = job_posting_id), 0),
                    %s)
                WHERE required_years IS NULL
                """.formatted(skillYears));
        if (legacyYears) jdbc.execute("ALTER TABLE skills DROP COLUMN years_of_experience");
    }
}
