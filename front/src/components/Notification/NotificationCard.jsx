import styles from "./styles/NotificationCard.module.css"
import profileViewLogo from "../../assets/eye-logo.svg";
import applicationReviewLogo from "../../assets/search-light-logo.svg";
import reachOutLogo from "../../assets/chat-logo.svg";

const TYPE_BADGES = {
    profileView: profileViewLogo,
    applicationReview: applicationReviewLogo,
    reachOut: reachOutLogo,
}

function NotificationCard({ logo, company, message, time, type, onClick }) {

    const badgeIcon = TYPE_BADGES[type]

    return (
        <button
            type="button"
            className={styles.notificationCard}
            onClick={onClick}
        >
            <div className={styles.avatarBox}>
                <img src={logo} alt={`${company} logo`} className={styles.avatar} />

                {badgeIcon && (
                    <img src={badgeIcon} alt={type} className={styles.badgeIcon} />
                )}
            </div>

            <div className={styles.textBlock}>
                <p className={styles.company}>{company}</p>
                <p className={styles.message}>{message}</p>
            </div>

            <div className={styles.metaBox}>
                <p className={styles.time}>{time}</p>
            </div>

            <span className={styles.readArrow}>❯</span>
        </button>
    )
}

export default NotificationCard