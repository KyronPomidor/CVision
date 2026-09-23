import styles from "./JobDescription.module.css";
import typography from "../../../../Typography.module.css";
import Star from "../../../../assets/star-logo.svg?react";

function JobDescription({ className='', skills, description, ...props }) {
  return (
    <div className={`${className} ${styles.container}`} {...props}>
      <h2 className={typography.heading2}>Job Description</h2>
      <p className={`${styles.body} ${typography.mainText}`}>
        {description}
      </p>
      <div className={styles.skillSection}>
        <span className={`${typography.mainTextImportant} ${styles.subTitle}`}>
            Necessary Skills (AI extracted)
        </span>
        <div className={styles.skillsRow}>
            {skills.map((skill) => (
            <span key={skill} className={`${typography.mainTextImportant} ${styles.skillPill}`}>
                {skill}
            </span>
            ))}
        </div>
      </div>
      <div className={styles.buttonRow}>
        <button className={`${typography.heading2} ${styles.apply}`}>Apply</button>
        <button className={styles.favorite}>
            <Star className={styles.starLogo}/>
        </button>
      </div>
    </div>
  );
}

export default JobDescription;
