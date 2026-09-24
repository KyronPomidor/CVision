import styles from "./TechnicalSkills.module.css";
import typography from "../../../../Typography.module.css";

function TechnicalSkills({ skills = [] }) {
  return (
    <div className={styles.technicalSkills}>
      <h2 className={typography.heading2}>Technical Skills</h2>

      <div className={styles.skillsList}>
        {skills.map((skill) => (
          <span key={skill} className={`${styles.skillPill} ${typography.mainTextImportant}`}>
            {skill}
          </span>
        ))}
      </div>
    </div>
  );
}

export default TechnicalSkills;
