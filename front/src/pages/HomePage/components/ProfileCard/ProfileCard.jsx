import styles from "./ProfileCard.module.css"
import profileLogo from "../../../../assets/profile-photo.png";
import ContactInfo from "./ContactInfo";
import AboutMe from "./AboutMe";
import Achievements from "./Achievements";

function ProfileCard({
    name,
    email,
    phone,
    location,
    aboutMe,
    appliedJobsCount,
    savedJobsCount,
    cvViewsCount,
    reachoutsCount,
    onEditProfile
}) {

    return (
        <div className={styles.profileCard}>
            <div className={styles.photoBox}>
                <img src={profileLogo} alt="profileLogo" className={styles.profileLogo} />
                <h2 className={styles.profileName}>{name}</h2>
            </div>

            <ContactInfo
                email={email}
                phone={phone}
                location={location}
            />

            <AboutMe
                aboutMe={aboutMe}
                onEditProfile={onEditProfile}
            />

            <Achievements
                appliedJobsCount={appliedJobsCount}
                savedJobsCount={savedJobsCount}
                cvViewsCount={cvViewsCount}
                reachoutsCount={reachoutsCount}
            />
        </div>
    )
}

export default ProfileCard