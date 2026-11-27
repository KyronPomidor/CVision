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
        jdbc.update("""
                UPDATE job_posting_skills SET required_years = GREATEST(
                    COALESCE((SELECT p.experience FROM job_postings p WHERE p.id = job_posting_id), 0),
                    COALESCE((SELECT s.years_of_experience FROM skills s WHERE s.id = skill_id), 0))
                WHERE required_years IS NULL
                """);
    }
}
