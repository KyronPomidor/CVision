import styles from "./ProfileCard.module.css";
import typography from "../../Typography.module.css";

import ContactInfo from "./ContactInfo";
import AboutMe from "./AboutMe";
import Achievements from "./Achievements";
import UploadIcon from "./assets/upload-logo.svg?react";
import PencilLogo from "../../assets/pencil-logo.svg?react";
import defaultPhoto from "../../assets/user-icon.svg";

function ProfileCard({
  variant = "home",
  name,
  photo,
  email,
  phone,
  location,
  aboutMe,
  appliedJobs,
  savedJobs,
  cvViews,
  reachouts,
  onEditProfile,
  onUploadCV,
  editing,
  saving,
  onSave,
  description,
  onDescriptionChange,
  onCancel,
}) {
  const isProfileVariant = variant === "profile";

  return (
    <div className={`${styles.profileCard} ${isProfileVariant ? styles.profileVariant : ""}`}>
      <div className={styles.photoBox}>
        <img src={photo || defaultPhoto} alt={`${name} profile`} className={styles.profileLogo} />
        <h2 className={`${styles.profileName} ${typography.heading2}`}>{name}</h2>
      </div>

      <ContactInfo email={email} phone={phone} location={location} />

      <AboutMe
        aboutMe={aboutMe}
        onEditProfile={onEditProfile}
        showEditButton={!isProfileVariant}
        editing={editing}
        saving={saving}
        description={description}
        onDescriptionChange={onDescriptionChange}
      />

      {isProfileVariant ? (
        <div className={styles.profileActions}>
          {!editing && (
            <button
              type="button"
              className={styles.uploadCvButton}
              onClick={onUploadCV}
              disabled={!onUploadCV}
            >
              <UploadIcon className={styles.uploadIcon} />
              <p className={`${styles.uploadContent} ${typography.heading2}`}>Upload CV</p>
            </button>
          )}
          <button
            type="button"
            className={styles.editButtonBottom}
            onClick={editing ? onSave : onEditProfile}
            disabled={saving}
          >
            {!editing && <PencilLogo className={styles.pencilLogo} />}
            <p className={`${styles.editContent} ${typography.heading2}`}>
              {editing ? (saving ? "Saving..." : "Save") : "Edit"}
            </p>
          </button>
          {editing && (
            <button
              type="button"
              className={styles.cancelButton}
              onClick={onCancel}
              disabled={saving}
            >
              Cancel
            </button>
          )}
        </div>
      ) : appliedJobs ? (
        <Achievements
          appliedJobs={appliedJobs}
          savedJobs={savedJobs}
          cvViews={cvViews}
          reachouts={reachouts}
        />
      ) : (
        <p>Activity statistics are not supported yet.</p>
      )}
    </div>
  );
}

export default ProfileCard;
