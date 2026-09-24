import styles from "./JobHeader.module.css";
import typography from "../../../../Typography.module.css";

function JobHeader({ className = "", logo, jobName, companyName, ...props }) {
  return (
    <div className={`${className} ${styles.container}`} {...props}>
      <img src={logo} alt="logo" className={styles.logo} />
      <div>
        <h1 className={typography.heading1}>{jobName}</h1>
        <h2 className={`${styles.companyName} ${typography.h2}`}>{companyName}</h2>
      </div>
    </div>
  );
}

export default JobHeader;
