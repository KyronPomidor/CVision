package com.pbl.back.service;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A deliberately transparent first-pass job-fit calculator. Scores are based
 * only on explicit skill overlap; they are not a prediction of hiring success.
 */
@Component
public class FitScoreCalculator {

    public FitScoreResult calculate(Collection<String> candidateSkills, Collection<String> jobSkills) {
        Set<String> candidate = normalize(candidateSkills);
        Set<String> required = normalize(jobSkills);

        if (required.isEmpty()) {
            return new FitScoreResult(0.0, Set.of(), Set.of());
        }

        Set<String> matched = new LinkedHashSet<>(required);
        matched.retainAll(candidate);

        Set<String> missing = new LinkedHashSet<>(required);
        missing.removeAll(candidate);

        double score = Math.round((matched.size() * 10000.0) / required.size()) / 100.0;
        return new FitScoreResult(score, matched, missing);
    }

    private Set<String> normalize(Collection<String> skills) {
        Set<String> normalized = new LinkedHashSet<>();
        if (skills == null) {
            return normalized;
        }
        for (String skill : skills) {
            if (skill != null && !skill.isBlank()) {
                normalized.add(skill.trim().toLowerCase(Locale.ROOT));
            }
        }
        return normalized;
    }

    public record FitScoreResult(double score, Set<String> matchedSkills, Set<String> missingSkills) {
    }
}
