import styles from "./ProfileCard.module.css";
import profileLogo from "../../assets/profile-photo.png";
import ContactInfo from "./ContactInfo";
import AboutMe from "./AboutMe";
import Achievements from "./Achievements";
import UploadIcon from "./assets/upload-logo.svg?react";

function ProfileCard({
  variant = "home",
  name,
  email,
  phone,
  location,
  aboutMe,
  appliedJobsCount,
  savedJobsCount,
  cvViewsCount,
  reachoutsCount,
  onEditProfile,
  onUploadCV,
}) {
  const isProfileVariant = variant === "profile";

  return (
        <div className={`${styles.profileCard} ${isProfileVariant ? styles.profileVariant : ""}`}>
      <div className={styles.photoBox}>
        <img src={profileLogo} alt="profileLogo" className={styles.profileLogo} />
        <h2 className={styles.profileName}>{name}</h2>
      </div>

      <ContactInfo email={email} phone={phone} location={location} />

      <AboutMe
        aboutMe={aboutMe}
        onEditProfile={onEditProfile}
        showEditButton={!isProfileVariant}
      />

      {isProfileVariant ? (
        <div className={styles.profileActions}>
          <button type="button" className={styles.uploadCvButton} onClick={onUploadCV}>
            <UploadIcon className={styles.uploadIcon} />
            <span>Upload CV</span>
          </button>
          <button type="button" className={styles.editButtonBottom} onClick={onEditProfile}>
            Edit
          </button>
        </div>
      ) : (
        <Achievements
          appliedJobsCount={appliedJobsCount}
          savedJobsCount={savedJobsCount}
          cvViewsCount={cvViewsCount}
          reachoutsCount={reachoutsCount}
        />
      )}
    </div>
  );
}

export default ProfileCard;