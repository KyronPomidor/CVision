import styles from "./JobOffersCard.module.css";
import MiniJobCard from "./MiniJobCard";

function JobOffersCard({ name, logo, jobs }) {
  return (
    <div className={styles.card}>
      <h2 className={styles.title}>Job offers from {name}</h2>
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
