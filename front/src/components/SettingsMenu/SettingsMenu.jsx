import styles from "./SettingsMenu.module.css";
import typography from "../../Typography.module.css";
import { logout } from "../../api/auth";

function SettingsMenu() {
  return (
    <div className={styles.menu}>
      <button type="button" className={styles.menuItem} onClick={logout}>
        <span className={`${typography.mainTextImportant} ${styles.text}`}>Sign out</span>
      </button>
    </div>
  );
}

export default SettingsMenu;
