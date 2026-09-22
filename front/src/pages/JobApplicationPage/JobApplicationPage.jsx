import styles from "./JobApplicationPage.module.css";

function JobApplicationPage() {
    return (
        <div className={styles.page}>
            <div className={`${styles.card} ${styles.jobHeader}`}></div>
            <div className={`${styles.card} ${styles.jobDescription}`}></div>
            <div className={`${styles.card} ${styles.sidebar}`}></div>
            <div className={`${styles.card} ${styles.companyGallery}`}></div>
            <div className={`${styles.card} ${styles.jobOffers}`}></div>
        </div>
    );
}

export default JobApplicationPage;
