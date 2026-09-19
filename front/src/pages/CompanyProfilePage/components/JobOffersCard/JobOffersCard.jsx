import styles from "./JobOffersCard.module.css"
import MiniJobCard from "./MiniJobCard";

import logo from "../CompanyHeaderCard/assets/roslinLogo.webp";

const jobs = [
    { id: 1, logo: logo, title: "Frontend Developer", schedule: "Flexible", salary: "$4,373 per year", experience: "No experience" },
    { id: 2, logo: logo, title: "Frontend Developer", schedule: "Flexible", salary: "$4,373 per year", experience: "No experience" },
    { id: 3, logo: logo, title: "Frontend Developer", schedule: "Flexible", salary: "$4,373 per year", experience: "No experience" },
];

function JobOffersCard() {
    return (
        <div className={styles.card}>
            <h2 className={styles.title}>Job offers from Roslin Solutions</h2>
            <ul className={styles.jobList}>
                {jobs.map((job) => (
                    <MiniJobCard key={job.id} {...job} />
                ))}
            </ul>
            <a className={styles.link} href="#">View all job offers from this company </a>
        </div>
    )
}

export default JobOffersCard