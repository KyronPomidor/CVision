import styles from "./CompanyHeaderCard.module.css";
import typography from "../../../../Typography.module.css"

function CompanyHeaderCard({ name, logo, category }) {
  return (
    <div className={styles.card}>
      <div className={styles.row}>
        <img className={styles.logo} src={logo} alt={`${name} logo`} />
        <div className={styles.info}>
          <h1 className={typography.heading1}>{name}</h1>
          <span className={`${typography.mainTextImportant} ${styles.tag}`}>{category}</span>
        </div>
      </div>
    </div>
  );
}

export default CompanyHeaderCard;
