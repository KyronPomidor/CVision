package com.pbl.back.config;

import com.pbl.back.domain.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl.back.domain.enums.*;
import com.pbl.back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Populates the database with dummy data. Only runs with the "seed" profile:
 *   ./mvnw spring-boot:run -Dspring-boot.run.profiles=seed
 * Safe to run repeatedly: it skips if the seed users already exist.
 * All seeded accounts use the password: Password123!
 */
@Component
@Profile("seed")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private static final String PASSWORD = "Password123!";

    private final UserRepository userRepository;
    private final CandidateProfileRepository profileRepository;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;
    private final ProfileSkillRepository profileSkillRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobPostingSkillRepository jobPostingSkillRepository;
    private final CVRepository cvRepository;
    private final CVSkillRepository cvSkillRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRecommendationRepository recommendationRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.findAll().stream().anyMatch(u -> "alice@cvision.test".equals(u.getEmail()))) {
            System.out.println("[DataSeeder] Seed data already present, skipping.");
            return;
        }

        // ---- Skills ----
        Map<String, Skill> skills = new LinkedHashMap<>();
        for (String n : List.of("Java", "Spring Boot", "React", "JavaScript", "TypeScript", "SQL",
                "Python", "Docker", "AWS", "Git", "Node.js", "Figma", "Kubernetes", "Machine Learning")) {
            Skill s = skillRepository.findAll().stream().filter(x -> x.getName().equalsIgnoreCase(n)).findFirst()
                    .orElseGet(() -> skillRepository.save(Skill.builder().name(n).build()));
            skills.put(n, s);
        }

        // ---- Employers + companies ----
        User emp1 = user("hr@endava.test", "Endava HR", Role.EMPLOYER);
        User emp2 = user("hr@pentalog.test", "Pentalog HR", Role.EMPLOYER);
        User emp3 = user("hr@amdaris.test", "Amdaris HR", Role.EMPLOYER);

        Company endava = company(emp1, "Endava", "Global technology services company building digital products.",
                "Chisinau, Moldova", "https://endava.com", "careers@endava.test", 11000, 2000);
        Company pentalog = company(emp2, "Pentalog", "Digital services and engineering talent platform.",
                "Chisinau, Moldova", "https://pentalog.com", "jobs@pentalog.test", 2000, 2003);
        Company amdaris = company(emp3, "Amdaris", "Custom software development partner.",
                "Iasi, Romania", "https://amdaris.com", "jobs@amdaris.test", 900, 2007);

        // ---- Candidates ----
        User alice = user("alice@cvision.test", "alice", Role.EMPLOYEE);
        User bob = user("bob@cvision.test", "bob", Role.EMPLOYEE);
        User carol = user("carol@cvision.test", "carol", Role.EMPLOYEE);

        CandidateProfile pa = profile(alice, "Alice Popescu", "Chisinau, Moldova", "BSc Computer Science, UTM",
                "3 years as a backend developer.", "Backend developer who loves clean APIs.");
        CandidateProfile pb = profile(bob, "Bob Ionescu", "Iasi, Romania", "BSc Informatics, UAIC",
                "2 years of frontend work.", "Frontend developer focused on React and UX.");
        CandidateProfile pc = profile(carol, "Carol Rusu", "Bucharest, Romania", "MSc Data Science, UPB",
                "4 years in data and ML.", "Data scientist interested in applied ML.");

        profileSkill(pa, skills.get("Java"), SkillLevel.ADVANCED, 3);
        profileSkill(pa, skills.get("Spring Boot"), SkillLevel.ADVANCED, 3);
        profileSkill(pa, skills.get("SQL"), SkillLevel.INTERMEDIATE, 3);
        profileSkill(pa, skills.get("Docker"), SkillLevel.BEGINNER, 1);
        profileSkill(pb, skills.get("React"), SkillLevel.ADVANCED, 2);
        profileSkill(pb, skills.get("JavaScript"), SkillLevel.ADVANCED, 2);
        profileSkill(pb, skills.get("TypeScript"), SkillLevel.INTERMEDIATE, 1);
        profileSkill(pb, skills.get("Figma"), SkillLevel.INTERMEDIATE, 2);
        profileSkill(pc, skills.get("Python"), SkillLevel.EXPERT, 4);
        profileSkill(pc, skills.get("Machine Learning"), SkillLevel.ADVANCED, 4);
        profileSkill(pc, skills.get("SQL"), SkillLevel.ADVANCED, 4);

        // ---- Job postings ----
        JobPosting j1 = job(endava, "Java Backend Developer", "Build and maintain Spring Boot microservices.",
                JobType.FULL_TIME_CONTRACT, WorkSetting.HYBRID, "Chisinau, Moldova", 2500, Schedule.FULL_TIME, 2,
                "BSc in CS or equivalent", "careers@endava.test", "Java", "Spring Boot", "SQL", "Docker");
        JobPosting j2 = job(endava, "React Frontend Developer", "Develop modern UIs with React and TypeScript.",
                JobType.FULL_TIME_CONTRACT, WorkSetting.REMOTE, "Remote", 2200, Schedule.FLEXIBLE, 1,
                "Any", "careers@endava.test", "React", "TypeScript", "JavaScript", "Git");
        JobPosting j3 = job(pentalog, "Data Scientist", "Develop ML models for customer analytics.",
                JobType.FULL_TIME_CONTRACT, WorkSetting.ON_SITE, "Bucharest, Romania", 3000, Schedule.FULL_TIME, 3,
                "MSc preferred", "jobs@pentalog.test", "Python", "Machine Learning", "SQL");
        JobPosting j4 = job(pentalog, "DevOps Intern", "Learn CI/CD, Docker and Kubernetes on real projects.",
                JobType.INTERNSHIP, WorkSetting.HYBRID, "Chisinau, Moldova", 600, Schedule.PART_TIME, 0,
                "Student", "jobs@pentalog.test", "Docker", "Kubernetes", "AWS", "Git");
        JobPosting j5 = job(amdaris, "Full-stack Engineer", "Work across Node.js APIs and React frontends.",
                JobType.FULL_TIME_CONTRACT, WorkSetting.HYBRID, "Iasi, Romania", 2800, Schedule.FULL_TIME, 3,
                "BSc in CS", "jobs@amdaris.test", "Node.js", "React", "TypeScript", "SQL");
        JobPosting j6 = job(amdaris, "UI/UX Designer", "Design product flows and prototypes.",
                JobType.TEMPORARY_CONTRACT, WorkSetting.REMOTE, "Remote", 1800, Schedule.FLEXIBLE, 2,
                "Any", "jobs@amdaris.test", "Figma", "JavaScript");
        j6.setStatus(JobStatus.CLOSED);
        jobPostingRepository.save(j6);

        // ---- CVs ----
        CV cvA = cv(alice, "alice_cv.pdf", "Java, Spring Boot, SQL, Docker", skills, "Java", "Spring Boot", "SQL", "Docker");
        CV cvB = cv(bob, "bob_cv.pdf", "React, JavaScript, TypeScript, Figma", skills, "React", "JavaScript", "TypeScript", "Figma");
        CV cvC = cv(carol, "carol_cv.pdf", "Python, Machine Learning, SQL", skills, "Python", "Machine Learning", "SQL");

        // ---- Applications ----
        application(alice, j1, cvA, ApplicationStatus.REVIEWING);
        application(bob, j2, cvB, ApplicationStatus.APPLIED);
        application(bob, j5, cvB, ApplicationStatus.REJECTED);
        application(carol, j3, cvC, ApplicationStatus.ACCEPTED);

        // ---- Recommendations ----
        recommend(alice, j1, 0.94, "Strong match on Java, Spring Boot and SQL.");
        recommend(alice, j5, 0.55, "Shares SQL; needs Node.js and React.");
        recommend(bob, j2, 0.92, "React and TypeScript experience matches the role.");
        recommend(bob, j5, 0.78, "Good React background; Node.js is a gap.");
        recommend(carol, j3, 0.97, "Python, ML and SQL align directly with the role.");

        System.out.println("[DataSeeder] Done. Login with any seeded email and password: " + PASSWORD);
    }

    private User user(String email, String name, Role role) {
        return userRepository.save(User.builder().email(email).accountName(name)
                .password(passwordEncoder.encode(PASSWORD)).role(role)
                .createdAt(LocalDateTime.now()).twoFactorEnabled(false).build());
    }

    private Company company(User owner, String name, String desc, String loc, String site, String email,
                            int size, int founded) {
        return companyRepository.save(Company.builder().user(owner).name(name).description(desc).location(loc)
                .details(CompanyDetails.builder().website(site).email(email).address(loc)
                        .size(size).founded(founded).build()).build());
    }

    private CandidateProfile profile(User u, String name, String loc, String edu, String exp, String desc) {
        return profileRepository.save(CandidateProfile.builder().user(u).name(name).location(loc)
                .education(edu).experience(exp).description(desc).build());
    }

    private void profileSkill(CandidateProfile p, Skill s, SkillLevel l, int years) {
        profileSkillRepository.save(ProfileSkill.builder().profile(p).skill(s).level(l).yearsOfExperience(years).build());
    }

    private JobPosting job(Company c, String title, String desc, JobType jt, WorkSetting ws, String loc,
                           int salary, Schedule sch, int exp, String edu, String contact, String... skillNames) {
        JobPosting jp = jobPostingRepository.save(JobPosting.builder().company(c).title(title).description(desc)
                .details(JobPostingDetails.builder().jobType(jt).workSetting(ws).location(loc)
                        .salary(BigDecimal.valueOf(salary)).schedule(sch).experience(exp)
                        .education(edu).contactEmail(contact).build())
                .createdAt(LocalDateTime.now()).status(JobStatus.OPEN).build());
        for (String n : skillNames) {
            Skill s = skillRepository.findAll().stream().filter(x -> x.getName().equals(n)).findFirst().orElseThrow();
            jobPostingSkillRepository.save(JobPostingSkill.builder().jobPosting(jp).skill(s).build());
        }
        return jp;
    }

    private CV cv(User u, String file, String skillsText, Map<String, Skill> skills, String... names) {
        CV cv = cvRepository.save(CV.builder().user(u).fileName(file).filePath("uploads/cvs/" + file)
                .extractedText("Dummy CV text for " + u.getAccountName()).skills(objectMapper.valueToTree(List.of(names)).toString())
                .uploadedAt(LocalDateTime.now()).build());
        for (String n : names) {
            cvSkillRepository.save(CVSkill.builder().cv(cv).skill(skills.get(n))
                    .confidenceScore(0.9).yearsOfExperience(2).build());
        }
        return cv;
    }

    private void application(User u, JobPosting jp, CV cv, ApplicationStatus st) {
        applicationRepository.save(Application.builder().user(u).jobPosting(jp).cv(cv)
                .status(st).appliedAt(LocalDateTime.now()).build());
    }

    private void recommend(User u, JobPosting jp, double score, String why) {
        recommendationRepository.save(JobRecommendation.builder().user(u).jobPosting(jp)
                .matchScore(score).explanation(why).createdAt(LocalDateTime.now()).build());
    }
}
