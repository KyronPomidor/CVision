import styles from "./JobOffersCard.module.css";
import typography from "../../../../Typography.module.css"
import MiniJobCard from "./MiniJobCard";

function JobOffersCard({ name, logo, jobs }) {
  return (
    <div className={styles.card}>
      <h2 className={typography.heading2}>Job offers from {name}</h2>
      <ul className={styles.jobList}>
        {jobs.map((job) => (
          <MiniJobCard key={job.id} logo={logo} {...job} />
        ))}
      </ul>
      <a className={styles.link} href="#">
        View all job offers from this company{" "}
      </a>
    </div>
  );
}

export default JobOffersCard;
