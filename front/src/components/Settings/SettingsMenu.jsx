import { Link } from "react-router-dom";
import styles from "./SettingsMenu.module.css";

function SettingsMenu() {
  return (
    <div className={styles.SettingsMenu}>
      <Link to="/login" className={styles.signOutButton}>
        Sign out
      </Link>
    </div>
  );
}

export default SettingsMenu;