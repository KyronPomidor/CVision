import styles from "./styles/Notifications.module.css"
import NotificationCard from "./NotificationCard"
import endavaLogo from "../../assets/endava-logo.png";
import pentalogLogo from "../../assets/pentalog-logo.png";
import roslinLogo from "../../assets/roslin-logo.png";
import amdarisLogo from "../../assets/amdaris-logo.png";

const DEFAULT_NOTIFICATIONS = [
    {
        id: 1,
        logo: roslinLogo,
        company: "Roslin Solutions",
        message: "checked your profile",
        time: "14:00",
        type: "profileView",
    },
    {
        id: 2,
        logo: pentalogLogo,
        company: "Pentalog",
        message: "analysed your application",
        time: "10:00",
        type: "applicationReview",
    },
    {
        id: 3,
        logo: endavaLogo,
        company: "Endava",
        message: "reaches out to you",
        time: "7:00",
        type: "reachOut",
    },
    {
        id: 4,
        logo: amdarisLogo,
        company: "Amdaris",
        message: "reaches out to you",
        time: "12.09.2026",
        type: "reachOut",
    },
]

function Notifications({
    notifications = DEFAULT_NOTIFICATIONS,
    onMarkAllRead,
    onViewAll,
    onNotificationClick
}) {

    return (
        <div className={styles.Notifications}>
            <div className={styles.header}>
                <h2 className={styles.headerTitle}>Notifications</h2>

                <button
                    type="button"
                    className={styles.markAllButton}
                    onClick={onMarkAllRead}
                >
                    Mark all as read
                </button>
            </div>

            <div className={styles.cardsList}>
                {notifications.map((notification) => (
                    <NotificationCard
                        key={notification.id}
                        logo={notification.logo}
                        company={notification.company}
                        message={notification.message}
                        time={notification.time}
                        type={notification.type}
                        onClick={() => onNotificationClick?.(notification.id)}
                    />
                ))}
            </div>

            <button
                type="button"
                className={styles.viewAllButton}
                onClick={onViewAll}
            >
                View all notifications
                <span className={styles.arrow}>&gt;</span>
            </button>
        </div>
    )
}

export default Notifications