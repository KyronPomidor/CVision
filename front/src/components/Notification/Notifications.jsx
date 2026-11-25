import styles from "./Notifications.module.css";
import NotificationCard from "./NotificationCard";
function Notifications({ notifications = [], onMarkAllRead, onViewAll, onNotificationClick }) {
  return (
    <div className={styles.Notifications}>
      <div className={styles.header}>
        <h2 className={styles.headerTitle}>Notifications</h2>

        <button
          type="button"
          className={styles.markAllButton}
          onClick={onMarkAllRead}
          disabled={!onMarkAllRead}
        >
          Mark all as read
        </button>
      </div>

      <div className={styles.cardsList}>
        {!notifications.length && <p>Notifications are not supported yet.</p>}
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
        disabled={!onViewAll}
      >
        View all notifications
        <span className={styles.arrow}>&gt;</span>
      </button>
    </div>
  );
}

export default Notifications;
