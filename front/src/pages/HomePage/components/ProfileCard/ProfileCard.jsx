import styles from "./ProfileCard.module.css"
import profileLogo from "../../../../assets/profile-photo.png";
import emailLogo from "../../../../assets/mail-icon.svg";
import telephoneLogo from "../../../../assets/telephone-logo.svg";
import locationLogo from "../../../../assets/location-logo.svg";
import pencilLogo from "../../../../assets/pencil-logo.svg";
import bagLogo from "../../../../assets/bag-logo.svg";
import starLogo from "../../../../assets/star-logo.svg";
import paperLogo from "../../../../assets/paper-logo.svg";
import envelopeLogo from "../../../../assets/envelope-logo.svg";

function ProfileCard () {

    return (
        <div className={styles.profileCard}>
            <div className={styles.photoBox}>
                <img src={profileLogo} alt="profileLogo" className={styles.profileLogo} />
                <h2 className={styles.profileName}>Nicholas Carnegie</h2>
            </div>

            <div className={styles.profileInfo}>
                <div className={styles.emailBox}>
                    <img src={emailLogo} alt="emailLogo" className={styles.emailLogo}/>
                    <p className={styles.emailText}>
                        nick.carn@gmail.com
                    </p>
                </div>

                <div className={styles.telephoneBox}>
                    <img src={telephoneLogo} alt="telephoneLogo" className={styles.telephoneLogo}/>
                    <p className={styles.telephoneText}>
                        +373 62 123 456
                    </p>
                </div>

                <div className={styles.locationBox}>
                    <img src={locationLogo} alt="locationLogo" className={styles.locationLogo}/>
                    <p className={styles.locationText}>
                        Chișinău, Moldova
                    </p>
                </div>
            </div>

            <div className={styles.aboutMeBox}>
                <div className={styles.aboutMeSection}>
                    <h2 className={styles.aboutMeHeading}>About Me</h2>
                    <p className={styles.aboutMeInfo}>
                        A junior IT professional passionate about technology and problem-solving.
                        I am eager to grow my skills, learn from experienced teams, and contribute 
                        to meaningful projects.
                    </p>
                </div>
                
                <form action="" className={styles.editForm}>
                    <button className={styles.editButton}>
                        <img src={pencilLogo} alt="pencilLogo" className={styles.pencilLogo}/>
                        <p className={styles.editText}>Edit Profile</p>
                    </button>
                </form>
            </div>

            <div className={styles.achievements}>
                <div className={styles.jobSection}>
                    <div className={styles.appliedJobs}>
                        <div className={styles.appliedJobs_contactItem}>
                            <img src={bagLogo} alt="bagLogo" className={styles.bagLogo}/>
                            <h2 className={styles.appliedJobs_quantity}>3</h2>
                        </div>

                        <p className={styles.appliedJobs_text}>Applied Jobs</p>
                    </div>

                    <div className={styles.savedJobs}>
                        <div className={styles.savedJobs_contactItem}>
                            <img src={starLogo} alt="starLogo" className={styles.starLogo}/>
                            <h2 className={styles.savedJobs_quantity}>2</h2>
                        </div>

                        <p className={styles.savedJobs_text}>Saved Jobs</p>
                    </div>
                </div>

                <div className={styles.personalSection}>
                    <div className={styles.curriculumViews}>
                        <div className={styles.curriculumViews_contactItem}>
                            <img src={paperLogo} alt="paperLogo" className={styles.paperLogo}/>
                            <h2 className={styles.curriculumViews_quantity}>1</h2>
                        </div>

                        <p className={styles.curriculumViews_text}>CV Views</p>
                    </div>

                    <div className={styles.reachouts}>
                        <div className={styles.reachouts_contactItem}>
                            <img src={envelopeLogo} alt="envelopeLogo" className={styles.envelopeLogo}/>
                            <h2 className={styles.reachouts_quantity}>10</h2>
                        </div>

                        <p className={styles.reachouts_text}>Reachouts</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProfileCard