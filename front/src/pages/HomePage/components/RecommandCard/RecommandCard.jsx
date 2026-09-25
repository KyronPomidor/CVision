import styles from "./RecommandCard.module.css";
import typography from "../../../../Typography.module.css";
import BookLogo from "./assets/book-logo.svg?react";
import JobCard from "./JobCard";

function RecommandCard({ jobs = [], savedJobs = [], appliedJobs = [], onToggleStar, onApply }) {
  return (
    <div className={styles.RecommandCard}>
      <div className={styles.header}>
        <BookLogo className={styles.headerIcon} />
        <h2 className={`${typography.heading1} ${styles.headerTitle}`}>
          Recommended Opportunities
        </h2>
      </div>

      <div className={styles.jobsList}>
        {jobs.map((job) => (
          <JobCard
            key={job.id}
            jobId={job.id}
            companyId={job.companyId}
            logo={job.logo}
            title={job.title}
            company={job.company}
            postedAt={job.postedAt}
            employmentType={job.employmentType}
            salary={job.salary}
            experience={job.experience}
            matchPercent={job.matchPercent}
            isStarred={savedJobs.includes(job.id)}
            isApplied={appliedJobs.includes(job.id)}
            onToggleStar={() => onToggleStar?.(job.id)}
            onApply={() => onApply?.(job.id)}
          />
        ))}
      </div>
    </div>
  );
}

export default RecommandCard;