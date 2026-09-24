import styles from "./AboutMe.module.css";
import typography from "../../Typography.module.css";

import pencilLogo from "../../assets/pencil-logo.svg";

function AboutMe({ aboutMe, onEditProfile, showEditButton = true }) {
  return (
    <div className={styles.aboutMeBox}>
      <div className={styles.aboutMeSection}>
        <h2 className={styles.aboutMeHeading}>About Me</h2>
        <p className={`${styles.aboutMeInfo} ${typography.mainText}`}>{aboutMe}</p>
      </div>

      {showEditButton && (
        <div className={styles.editButtonWrapper}>
          {/*
            TODO: once routing is set up, replace onEditProfile with real
            navigation, e.g.:
              const navigate = useNavigate();
              <button onClick={() => navigate("/profile/edit")}>
          */}
          <button type="button" className={styles.editButton} onClick={onEditProfile}>
            <img src={pencilLogo} alt="pencilLogo" className={styles.pencilLogo} />
            <p className={styles.editText}>Edit Profile</p>
          </button>
        </div>
      )}
    </div>
  );
}

export default AboutMe;
