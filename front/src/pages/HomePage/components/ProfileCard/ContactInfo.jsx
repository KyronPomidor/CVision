import styles from "./ContactInfo.module.css";
import typography from "../../../../Typography.module.css";
import EmailLogo from "../../../../assets/mail-icon.svg?react";
import TelephoneLogo from "../../../../assets/telephone-logo.svg?react";
import LocationLogo from "../../../../assets/location-logo.svg?react";

function ContactInfo({ email, phone, location }) {
  return (
    <div className={styles.contactInfo}>
      <div className={styles.emailBox}>
        <EmailLogo className={styles.emailLogo} />
        <p className={typography.mainTextImportant}>{email}</p>
      </div>

      <div className={styles.telephoneBox}>
        <TelephoneLogo className={styles.telephoneLogo} />
        <p className={typography.mainTextImportant}>{phone}</p>
      </div>

      <div className={styles.locationBox}>
        <LocationLogo className={styles.locationLogo} />
        <p className={typography.mainTextImportant}>{location}</p>
      </div>
    </div>
  );
}

export default ContactInfo;
