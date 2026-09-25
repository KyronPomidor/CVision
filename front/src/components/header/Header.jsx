import { useState } from "react";
import { Link } from "react-router-dom";
import styles from "./Header.module.css";
import Notifications from "../Notification/Notifications";
import SettingsMenu from "../Settings/SettingsMenu";
import mainLogo from "../../../../Logo.svg";
import SearchLogo from "../../assets/search-icon.svg?react";
import SettingsLogo from "./assets/settings-icon.svg?react";
import NotificationLogo from "./assets/notification-icon.svg?react";
import profileLogo from "../../assets/profile-photo.png";

function Header() {
  const [isNotificationsOpen, setIsNotificationsOpen] = useState(false);
  const [isSettingsOpen, setIsSettingsOpen] = useState(false);

  return (
    <header className={styles.header}>
      <div className={styles.logoBox}>
        <Link to="/">
          <img src={mainLogo} alt="mainLogo" className={styles.mainLogo} />
        </Link>
      </div>

      <div className={styles.searchBox}>
        <form className={styles.searchBar} /* TODO: Back implementation*/>
          <input
            type="text"
            className={styles.searchInput}
            placeholder="Job title, Salary, or Companies...."
            aria-label="Search jobs"

            //TODO: Back implementation
          />

          <button type="submit" className={styles.searchButton} aria-label="Search button">
            <SearchLogo className={styles.searchLogo} />
          </button>
        </form>
      </div>
      <div className={styles.toolsBox}>
        <div className={styles.toolsChoose}>
          <button
            type="button"
            className={styles.setingsButton}
            aria-label="Settings button"
            aria-expanded={isSettingsOpen}
            onClick={() => setIsSettingsOpen((prev) => !prev)}
          >
            <SettingsLogo className={styles.settingsLogo} />
          </button>

          <button
            type="button"
            className={styles.notificationButton}
            aria-label="Notification button"
            aria-expanded={isNotificationsOpen}
            onClick={() => setIsNotificationsOpen((prev) => !prev)}
          >
            <NotificationLogo className={styles.notificationLogo} />
          </button>

          <Link to="/profile" className={styles.profileButton} aria-label="Profile button">
            <img src={profileLogo} alt="profileLogo" className={styles.profileLogo} />
          </Link>
        </div>
      </div>

      {isNotificationsOpen && <Notifications />}
      {isSettingsOpen && <SettingsMenu />}
    </header>
  );
}

export default Header;