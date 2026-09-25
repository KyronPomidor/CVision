import { Link } from "react-router-dom";
import styles from "./JobCard.module.css";
import typography from "../../../../Typography.module.css";
import ClockLogo from "../../../../assets/clock-logo.svg?react";
import DollarLogo from "../../../../assets/dollar-logo.svg?react";
import BriefcaseLogo from "../../../../assets/briefcase-logo.svg?react";
import StarOutlineLogo from "../../../../assets/star-logo.svg?react";

function getPostedTime(dateString) {
  const postedDate = new Date(dateString);
  const now = new Date();

  const diffMs = now - postedDate;
  const diffMinutes = Math.floor(diffMs / (1000 * 60));

  if (diffMinutes < 1) return "Posted just now";

  if (diffMinutes < 60) {
    return `Posted ${diffMinutes} min ago`;
  }

  const diffHours = Math.floor(diffMinutes / 60);

  if (diffHours < 24) {
    return `Posted ${diffHours} ${diffHours === 1 ? "hour" : "hours"} ago`;
  }

  const diffDays = Math.floor(diffHours / 24);

  if (diffDays < 7) {
    return `Posted ${diffDays} ${diffDays === 1 ? "day" : "days"} ago`;
  }

  const diffWeeks = Math.floor(diffDays / 7);

  if (diffWeeks < 4) {
    return `Posted ${diffWeeks} ${diffWeeks === 1 ? "week" : "weeks"} ago`;
  }

  return `Posted on ${postedDate.toLocaleDateString()}`;
}

function getMatchLevel(matchPercent) {
  if (matchPercent >= 80) return "green";
  if (matchPercent >= 40) return "olive";
  return "red";
}

function JobCard({
  jobId,
  companyId,
  logo,
  title,
  company,
  postedAt,
  employmentType,
  salary,
  experience,
  matchPercent,
  isStarred,
  isApplied,
  onToggleStar,
  onApply,
}) {
  const matchLevel = getMatchLevel(matchPercent);
  const postedText = getPostedTime(postedAt);

  return (
    <div className={styles.jobCard}>
      <div className={styles.topRow}>
        <div className={styles.companyInfo}>
          <Link to={`/companies/${companyId}`}> 
            <img src={logo} alt={`${company} logo`} className={styles.companyLogo} />
          </Link>

          <div className={styles.titleBlock}>
            <Link to={`/jobs/${jobId}`}>
              <h3 className={`${typography.heading2} ${styles.jobTitle}`}>{title}</h3>
            </Link>
            <Link to={`/companies/${companyId}`} className={styles.companyName}>
              {company}
            </Link>
          </div>
        </div>


        <div className={styles.metaRight}>
          <p className={`${typography.mainText} ${styles.postedText}`}>{postedText}</p>

          <button
            type="button"
            className={styles.starButton}
            onClick={onToggleStar}
            aria-pressed={isStarred}
          >
            <StarOutlineLogo className={isStarred ? styles.starActive : styles.starIcon} />
          </button>
        </div>
      </div>

      <div className={styles.detailsRow}>
        <div className={styles.detailItem}>
          <ClockLogo className={styles.clockLogo} />
          <p className={typography.mainText}>{employmentType}</p>
        </div>

        <div className={styles.detailItem}>
          <DollarLogo className={styles.dollarLogo} />
          <p className={typography.mainText}>{salary}</p>
        </div>

        <div className={styles.detailItem}>
          <BriefcaseLogo className={styles.briefcaseLogo} />
          <p className={typography.mainText}>{experience}</p>
        </div>
      </div>

      <div className={styles.matchSection}>
        <p className={typography.mainTextImportant}>
          <span className={`${styles.matchPercent} ${styles[matchLevel]}`}>
            {matchPercent}% Match
          </span>{" "}
          based on your CV
        </p>

        <div className={styles.progressTrack}>
          <div
            className={`${styles.progressFill} ${styles[matchLevel]}`}
            style={{ width: `${matchPercent}%` }}
          />
        </div>
      </div>

      <div className={styles.bottomRow}>
        <button
          type="button"
          className={styles.applyButton}
          onClick={onApply}
          disabled={isApplied}
        >
          {isApplied ? "Applied" : "Apply"}
        </button>
      </div>
    </div>
  );

}

export default JobCard;
