import styles from "./JobApplicationPage.module.css";

import JobHeader from "./components/JobHeader";

function JobApplicationPage() {
    return (
        <div className={styles.page}>
            <JobHeader className={`${styles.card} ${styles.jobHeader}`} />
            <div className={`${styles.card} ${styles.jobDescription}`}></div>
            <div className={`${styles.card} ${styles.sidebar}`}></div>
            <div className={`${styles.card} ${styles.companyGallery}`}></div>
            <div className={`${styles.card} ${styles.jobOffers}`}></div>
        </div>
    );
}

export default JobApplicationPage;
