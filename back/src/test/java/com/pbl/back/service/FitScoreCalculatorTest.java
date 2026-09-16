package com.pbl.back.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FitScoreCalculatorTest {

    private final FitScoreCalculator calculator = new FitScoreCalculator();

    @Test
    void calculatesSkillCoverageCaseInsensitively() {
        FitScoreCalculator.FitScoreResult result = calculator.calculate(
                List.of("Java", "Spring Boot", "PostgreSQL"),
                List.of("java", "Spring Boot", "Docker", "postgresql"));

        assertEquals(75.0, result.score());
        assertEquals(3, result.matchedSkills().size());
        assertEquals(List.of("docker"), List.copyOf(result.missingSkills()));
    }

    @Test
    void returnsZeroWhenTheJobHasNoSkills() {
        FitScoreCalculator.FitScoreResult result = calculator.calculate(List.of("Java"), List.of());

        assertEquals(0.0, result.score());
    }
}
