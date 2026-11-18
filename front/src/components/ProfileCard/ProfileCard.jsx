import styles from "./ProfileCard.module.css";
import typography from "../../Typography.module.css";

import ProfileAvatar from "../ProfileAvatar/ProfileAvatar";
import ContactInfo from "./ContactInfo";
import AboutMe from "./AboutMe";
import Achievements from "./Achievements";
import UploadIcon from "./assets/upload-logo.svg?react";
import PencilLogo from "../../assets/pencil-logo.svg?react";

function ProfileCard({
  variant = "home",
  name,
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
  isEditing = false,
  description,
  onDescriptionChange,
  onSaveProfile,
  onCancelEdit,
  saving = false,
  saveError,
}) {
  const isProfileVariant = variant === "profile";

  return (
    <div className={`${styles.profileCard} ${isProfileVariant ? styles.profileVariant : ""}`}>
      <div className={styles.photoBox}>
        <ProfileAvatar name={name} className={styles.profileLogo} />
        <h2 className={`${styles.profileName} ${typography.heading2}`}>{name}</h2>
      </div>

      <ContactInfo email={email} phone={phone} location={location} />

      <AboutMe
        aboutMe={aboutMe}
        onEditProfile={onEditProfile}
        showEditButton={!isProfileVariant}
        isEditing={isEditing}
        description={description}
        onDescriptionChange={onDescriptionChange}
        saving={saving}
      />

      {isProfileVariant ? (
        <div className={styles.profileActions}>
          {!isEditing && (
            <button type="button" className={styles.uploadCvButton} onClick={onUploadCV}>
              <UploadIcon className={styles.uploadIcon} />
              <p className={`${styles.uploadContent} ${typography.heading2}`}>Upload CV</p>
            </button>
          )}
          <button
            type="button"
            className={styles.editButtonBottom}
            onClick={isEditing ? onSaveProfile : onEditProfile}
            disabled={saving}
          >
            {!isEditing && <PencilLogo className={styles.pencilLogo} />}
            <p className={`${styles.editContent} ${typography.heading2}`}>
              {isEditing ? (saving ? "Saving..." : "Save") : "Edit"}
            </p>
          </button>
          {isEditing && (
            <button
              type="button"
              className={styles.cancelButton}
              onClick={onCancelEdit}
              disabled={saving}
            >
              Cancel
            </button>
          )}
          {saveError && <p role="alert">{saveError}</p>}
        </div>
      ) : (
        <Achievements
          appliedJobs={appliedJobs}
          savedJobs={savedJobs}
          cvViews={cvViews}
          reachouts={reachouts}
        />
      )}
    </div>
  );
}

export default ProfileCard;
