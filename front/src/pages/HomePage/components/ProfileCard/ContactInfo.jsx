import styles from "./styles/ContactInfo.module.css"
import emailLogo from "../../../../assets/mail-icon.svg";
import telephoneLogo from "../../../../assets/telephone-logo.svg";
import locationLogo from "../../../../assets/location-logo.svg";

function ContactInfo({ email, phone, location }) {

    return (
        <div className={styles.contactInfo}>
            <div className={styles.emailBox}>
                <img src={emailLogo} alt="emailLogo" className={styles.emailLogo}/>
                <p className={styles.emailText}>
                    {email}
                </p>
            </div>

            <div className={styles.telephoneBox}>
                <img src={telephoneLogo} alt="telephoneLogo" className={styles.telephoneLogo}/>
                <p className={styles.telephoneText}>
                    {phone}
                </p>
            </div>

            <div className={styles.locationBox}>
                <img src={locationLogo} alt="locationLogo" className={styles.locationLogo}/>
                <p className={styles.locationText}>
                    {location}
                </p>
            </div>
        </div>
    )
}

export default ContactInfo
