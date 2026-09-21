import styles from "./JobCard.module.css";
import ClockLogo from "../../../../assets/clock-logo.svg?react";
import DollarLogo from "../../../../assets/dollar-logo.svg?react";
import BriefcaseLogo from "../../../../assets/briefcase-logo.svg?react";
import StarOutlineLogo from "../../../../assets/star-logo.svg?react";

function getMatchLevel(matchPercent) {
  if (matchPercent >= 80) return "green";
  if (matchPercent >= 40) return "olive";
  return "red";
}

function JobCard({
  logo,
  title,
  company,
  postedAt,
  employmentType,
  salary,
  experience,
  matchPercent,
  isStarred,
  onToggleStar,
  onApply,
}) {
  const matchLevel = getMatchLevel(matchPercent);

  return (
    <div className={styles.jobCard}>
      <div className={styles.topRow}>
        <div className={styles.companyInfo}>
          <img src={logo} alt={`${company} logo`} className={styles.companyLogo} />

          <div className={styles.titleBlock}>
            <h3 className={styles.jobTitle}>{title}</h3>
            <p className={styles.companyName}>{company}</p>
          </div>
        </div>

        <div className={styles.metaRight}>
          <p className={styles.postedText}>{postedAt}</p>

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
          <p>{employmentType}</p>
        </div>

        <div className={styles.detailItem}>
          <DollarLogo className={styles.dollarLogo} />
          <p>{salary}</p>
        </div>

        <div className={styles.detailItem}>
          <BriefcaseLogo className={styles.briefcaseLogo} />
          <p>{experience}</p>
        </div>
      </div>

      <div className={styles.matchSection}>
        <p className={styles.matchLabel}>
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
        <button type="button" className={styles.applyButton} onClick={onApply}>
          Apply
        </button>
      </div>
    </div>
  );
}

export default JobCard;
