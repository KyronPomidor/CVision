import styles from "./CompanyHeaderCard.module.css";

function CompanyHeaderCard({ name, logo, category }) {
  return (
    <div className={styles.card}>
      <div className={styles.row}>
        <img className={styles.logo} src={logo} alt={`${name} logo`} />
        <div className={styles.info}>
          <h1 className={styles.name}>{name}</h1>
          <span className={styles.tag}>{category}</span>
        </div>
      </div>
    </div>
  );
}

export default CompanyHeaderCard;
