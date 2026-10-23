import { useState } from "react";
import { Link } from "react-router-dom";
import styles from "./Header.module.css";
import Notifications from "../Notification/Notifications";
import SettingsMenu from "../SettingsMenu/SettingsMenu";
import mainLogo from "../../../../Logo.svg";
import SearchLogo from "../../assets/search-icon.svg?react";
import SettingsLogo from "./assets/settings-icon.svg?react";
import NotificationLogo from "./assets/notification-icon.svg?react";
import profileLogo from "../../assets/profile-photo.png";

function Header() {
  const [openMenu, setOpenMenu] = useState(null);

  function toggleMenu(menu) {
    setOpenMenu((prev) => (prev === menu ? null : menu));
  }

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
          <div className={styles.settingsRelative}>
            <button
              type="button"
              className={styles.settingsButton}
              aria-label="Settings button"
              onClick={() => toggleMenu("settings")}
            >
              <SettingsLogo className={styles.settingsLogo} />
            </button>

            {openMenu === "settings" && <SettingsMenu />}
          </div>

          <button
            type="button"
            className={styles.notificationButton}
            aria-label="Notification button"
            onClick={() => toggleMenu("notifications")}
          >
            <NotificationLogo className={styles.notificationLogo} />
          </button>

          <Link to="/profile" className={styles.profileButton} aria-label="Profile button">
            <img src={profileLogo} alt="profileLogo" className={styles.profileLogo} />
          </Link>
        </div>
      </div>

      {openMenu === "notifications" && <Notifications />}
    </header>
  );
}

export default Header;
