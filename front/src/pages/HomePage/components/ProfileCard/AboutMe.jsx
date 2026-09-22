import styles from "./AboutMe.module.css";
import typography from "../../../../Typography.module.css";
import pencilLogo from "../../../../assets/pencil-logo.svg";

function AboutMe({ aboutMe, onEditProfile }) {
  return (
    <div className={styles.aboutMeBox}>
      <div className={styles.aboutMeSection}>
        <h2 className={`${typography.heading2} ${styles.aboutMeHeading}`}>About Me</h2>
        <p className={`${typography.mainText} ${styles.aboutMeInfo}`}>{aboutMe}</p>
      </div>

      <div className={styles.editButtonWrapper}>
        {/*
                  TODO: once routing is set up, replace onEditProfile with real
                  navigation, e.g.:
                    const navigate = useNavigate();
                    <button onClick={() => navigate("/profile/edit")}>
                  For now this stays a plain callback prop so HomePage decides
                  what "edit" means until the router exists.
                */}
        <button type="button" className={styles.editButton} onClick={onEditProfile}>
          <img src={pencilLogo} alt="pencilLogo" className={styles.pencilLogo} />
          <p className={styles.editText}>Edit Profile</p>
        </button>
      </div>
    </div>
  );
}

export default AboutMe;
