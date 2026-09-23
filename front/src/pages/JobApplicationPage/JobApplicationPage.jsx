import styles from "./JobApplicationPage.module.css";

import JobHeader from "./components/JobHeader/JobHeader";
import JobDescription from "./components/JobDescription/JobDescription";
import Sidebar from "./components/Sidebar/Sidebar";

function JobApplicationPage() {
    return (
        <div className={styles.page}>
            <JobHeader className={`${styles.card} ${styles.jobHeader}`} />
            <JobDescription className={`${styles.card} ${styles.jobDescription}`} />
            <Sidebar className={`${styles.sidebar}`} />
            <div className={`${styles.card} ${styles.companyGallery}`}></div>
            <div className={`${styles.card} ${styles.jobOffers}`}></div>
        </div>
    );
}

export default JobApplicationPage;
