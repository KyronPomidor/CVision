import styles from "./CompanyDetailsCard.module.css";
import DetailRow from "./DetailRow";
import globe from "./assets/Navigation/Globe.svg";
import mail from "./assets/Communication/Mail.svg";
import mapPin from "./assets/Navigation/Map_Pin.svg";
import users from "./assets/User/Users.svg";
import calendar from "./assets/Calendar/Calendar_Days.svg";

import linkedin from "./assets/Socials/linkedin.svg"
import facebook from "./assets/Socials/facebook.svg"
import instagram from "./assets/Socials/instagram.svg"
import twitter from "./assets/Socials/twitter.svg"

const DETAIL_ROWS = [
    { key: "website", label: "Website",       icon: globe },
    { key: "email",   label: "Contact Email", icon: mail },
    { key: "address", label: "Location",      icon: mapPin },
    { key: "size",    label: "Size",          icon: users },
    { key: "founded", label: "Founded",       icon: calendar },
];

const SOCIALS = [
    { key: "linkedin",  label: "LinkedIn",  icon: linkedin },
    { key: "twitter",   label: "X",         icon: twitter },
    { key: "instagram", label: "Instagram", icon: instagram },
    { key: "facebook",  label: "Facebook",  icon: facebook },
];

function CompanyDetailsCard({ details }) {
    const { socials = {} } = details;
    const availableSocials = SOCIALS.filter(({ key }) => socials[key]);

    return (
        <div className={styles.card}>
            <h2 className={styles.title}>Company Details</h2>
            <div className={styles.details}>
                {DETAIL_ROWS.map(({ key, label, icon }) => (
                        <DetailRow
                            key={key}
                            icon={icon}
                            label={label}
                            value={details[key]}
                        />
                ))}
            </div>
            {availableSocials.length > 0 && (
                <div className={styles.social}>
                    <span className={styles.subTitle}>Social Media</span>
                    <div className={styles.socialList}>
                        {availableSocials.map(({ key, label, icon }) => (
                            <a
                                key={key}
                                href={socials[key]}
                                target="_blank"
                                rel="noreferrer"
                                aria-label={label}
                                className={styles.socialLink}
                            >
                                <img src={icon} alt="" className={styles.socialIcon} />
                            </a>
                        ))}
                    </div>
                </div>
            )}
        </div>
    )
}

export default CompanyDetailsCard