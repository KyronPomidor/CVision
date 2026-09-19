import styles from "./styles/Achievements.module.css"
import bagLogo from "../../../../assets/bag-logo.svg";
import starLogo from "../../../../assets/star-logo.svg";
import paperLogo from "../../../../assets/paper-logo.svg";
import envelopeLogo from "../../../../assets/envelope-logo.svg";

function Achievements({ appliedJobsCount, savedJobsCount, cvViewsCount, reachoutsCount }) {

    return (
        <div className={styles.achievements}>
            <div className={styles.jobSection}>
                <div className={styles.jobSection_leftPart}>
                    <div className={styles.appliedJobs}>
                        <div className={styles.appliedJobs_contactItem}>
                            <div className={styles.appliedJobs_imageBox}>
                                <img src={bagLogo} alt="bagLogo" className={styles.bagLogo}/>
                            </div>
                            <h2 className={styles.appliedJobs_quantity}>{appliedJobsCount}</h2>
                        </div>

                        <p className={styles.appliedJobs_text}>Applied Jobs</p>
                    </div>
                </div>

                <div className={styles.jobSection_rightPart}>
                    <div className={styles.savedJobs}>
                        <div className={styles.savedJobs_contactItem}>
                            <div className={styles.appliedJobs_imageBox}>
                                <img src={starLogo} alt="starLogo" className={styles.starLogo}/>
                            </div>
                            <h2 className={styles.savedJobs_quantity}>{savedJobsCount}</h2>
                        </div>

                        <p className={styles.savedJobs_text}>Saved Jobs</p>
                    </div>
                </div>
            </div>

            <div className={styles.personalSection}>

                <div className={styles.jobSection_leftPart}>
                    <div className={styles.curriculumViews}>
                        <div className={styles.curriculumViews_contactItem}>
                            <div className={styles.appliedJobs_imageBox}>
                                <img src={paperLogo} alt="paperLogo" className={styles.paperLogo}/>
                            </div>
                            <h2 className={styles.curriculumViews_quantity}>{cvViewsCount}</h2>
                        </div>

                        <p className={styles.curriculumViews_text}>CV Views</p>
                    </div>
                </div>

                <div className={styles.jobSection_rightPart}>
                    <div className={styles.reachouts}>
                        <div className={styles.reachouts_contactItem}>
                            <div className={styles.appliedJobs_imageBox}>
                                <img src={envelopeLogo} alt="envelopeLogo" className={styles.envelopeLogo}/>
                            </div>
                            <h2 className={styles.reachouts_quantity}>{reachoutsCount}</h2>
                        </div>

                        <p className={styles.reachouts_text}>Reachouts</p>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default Achievements
