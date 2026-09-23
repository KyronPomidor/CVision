import styles from "./Achievements.module.css";
import typography from "../../Typography.module.css";

import BagLogo from "./assets/bag-logo.svg?react";
import StarLogo from "../../assets/star-logo.svg?react";
import PaperLogo from "./assets/paper-logo.svg?react";
import EnvelopeLogo from "./assets/envelope-logo.svg?react";

function Achievements({ appliedJobsCount, savedJobsCount, cvViewsCount, reachoutsCount }) {
  return (
    <div className={styles.achievements}>
      <div className={styles.jobSection}>
        <div className={styles.jobSection_leftPart}>
          <div className={styles.appliedJobs}>
            <div className={styles.appliedJobs_contactItem}>
              <div className={styles.appliedJobs_imageBox}>
                <BagLogo className={styles.bagLogo} />
              </div>
              <h2 className={typography.heading2}>{appliedJobsCount}</h2>
            </div>

            <p className={typography.mainTextImportant}>Applied Jobs</p>
          </div>
        </div>

        <div className={styles.jobSection_rightPart}>
          <div className={styles.savedJobs}>
            <div className={styles.savedJobs_contactItem}>
              <div className={styles.appliedJobs_imageBox}>
                <StarLogo className={styles.starLogo} />
              </div>
              <h2 className={typography.heading2}>{savedJobsCount}</h2>
            </div>

            <p className={typography.mainTextImportant}>Saved Jobs</p>
          </div>
        </div>
      </div>

      <div className={styles.personalSection}>
        <div className={styles.jobSection_leftPart}>
          <div className={styles.curriculumViews}>
            <div className={styles.curriculumViews_contactItem}>
              <div className={styles.appliedJobs_imageBox}>
                <PaperLogo className={styles.paperLogo} />
              </div>
              <h2 className={typography.heading2}>{cvViewsCount}</h2>
            </div>

            <p className={typography.mainTextImportant}>CV Views</p>
          </div>
        </div>

        <div className={styles.jobSection_rightPart}>
          <div className={styles.reachouts}>
            <div className={styles.reachouts_contactItem}>
              <div className={styles.appliedJobs_imageBox}>
                <EnvelopeLogo className={styles.envelopeLogo} />
              </div>
              <h2 className={typography.heading2}>{reachoutsCount}</h2>
            </div>

            <p className={typography.mainTextImportant}>Reachouts</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Achievements;
