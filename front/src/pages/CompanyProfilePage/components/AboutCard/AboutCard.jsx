import styles from "./AboutCard.module.css";
import typography from "../../../../Typography.module.css";
import Perk from "./Perk";

function AboutCard({ name, description, perks }) {
  return (
    <div className={styles.card}>
      <h2 className={typography.heading2}>About {name}</h2>
      <div className={`${typography.mainText} ${styles.text}`}>
        {description
          .split("\n")
          .filter(Boolean)
          .map((paragraph, i) => (
            <p key={i}>{paragraph}</p>
          ))}
      </div>
      <div className={styles.perkRow}>
        {perks.map((type) => (
          <Perk key={type} type={type} />
        ))}
      </div>
    </div>
  );
}

export default AboutCard;
