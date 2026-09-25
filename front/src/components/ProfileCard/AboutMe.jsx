import { Link } from "react-router-dom";
import styles from "./AboutMe.module.css";
import typography from "../../Typography.module.css";

import pencilLogo from "../../assets/pencil-logo.svg";

function AboutMe({ aboutMe, showEditButton = true }) {
  return (
    <div className={styles.aboutMeBox}>
      <div className={styles.aboutMeSection}>
        <h2 className={styles.aboutMeHeading}>About Me</h2>
        <p className={`${styles.aboutMeInfo} ${typography.mainText}`}>{aboutMe}</p>
      </div>

      {showEditButton && (
        <div className={styles.editButtonWrapper}>
          <Link to="/profile" className={styles.editButton} aria-label="Edit profile">
            <img src={pencilLogo} alt="pencilLogo" className={styles.pencilLogo} />
            <p className={styles.editText}>Edit Profile</p>
          </Link>
        </div>
      )}
    </div>
  );
}

export default AboutMe;