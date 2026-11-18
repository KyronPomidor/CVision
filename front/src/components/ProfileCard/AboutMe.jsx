import styles from "./AboutMe.module.css";
import typography from "../../Typography.module.css";

import pencilLogo from "../../assets/pencil-logo.svg";

function AboutMe({
  aboutMe,
  onEditProfile,
  showEditButton = true,
  isEditing,
  description,
  onDescriptionChange,
  saving,
}) {
  return (
    <div className={styles.aboutMeBox}>
      <div className={styles.aboutMeSection}>
        <h2 className={styles.aboutMeHeading}>About Me</h2>
        {isEditing ? (
          <textarea
            className={`${styles.aboutMeInput} ${typography.mainText}`}
            aria-label="About Me"
            placeholder="Tell us about yourself"
            value={description}
            onChange={(event) => onDescriptionChange(event.target.value)}
            maxLength={5000}
            rows={1}
            disabled={saving}
            autoFocus
          />
        ) : (
          <p className={`${styles.aboutMeInfo} ${typography.mainText}`}>
            {aboutMe?.trim() || "Not specified yet"}
          </p>
        )}
      </div>

      {showEditButton && (
        <div className={styles.editButtonWrapper}>
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
