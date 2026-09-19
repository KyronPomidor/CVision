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

const SOCIALS = [
    { key: "linkedin",  label: "LinkedIn",  icon: linkedin },
    { key: "twitter",   label: "X",         icon: twitter },
    { key: "instagram", label: "Instagram", icon: instagram },
    { key: "facebook",  label: "Facebook",  icon: facebook },
];

function CompanyDetailsCard({ socials = {} }) {
    const availableSocials = SOCIALS.filter(({ key }) => socials[key]);

    return (
        <div className={styles.card}>
            <h2 className={styles.title}>Company Details</h2>
            <div className={styles.details}>
                <DetailRow 
                    icon={globe}
                    label="Website"
                    value="www.roslin.us"
                />
                <DetailRow 
                    icon={mail}
                    label="Contact Email"
                    value="hr@roslin.us"
                />
                <DetailRow 
                    icon={mapPin}
                    label="Location"
                    value="182 Stefan cel Mare Street, Chisinau, Moldova"
                />
                <DetailRow 
                    icon={users}
                    label="Size"
                    value="500+"
                />
                <DetailRow 
                    icon={calendar}
                    label="Founded"
                    value="2019"
                />
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