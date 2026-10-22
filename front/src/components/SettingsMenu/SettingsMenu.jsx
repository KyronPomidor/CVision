import typography from "../../Typography.module.css";
import { logout, enableTwoFactor, disableTwoFactor } from "../../api/auth";
import styles from "./SettingsMenu.module.css";

function SettingsMenu() {
  return (
    <div className={styles.menu}>
      <button type="button" className={styles.menuItem} onClick={logout}>
        <span className={`${typography.mainTextImportant} ${styles.text}`}>Sign out</span>
      </button>
      <button type="button" className={styles.menuItem} onClick={() => enableTwoFactor().catch(() => { })}>
        <span className={`${typography.mainTextImportant} ${styles.text}`}>Enable 2FA</span>
      </button>
      <button type="button" className={styles.menuItem} onClick={() => disableTwoFactor().catch(() => { })}>
        <span className={`${typography.mainTextImportant} ${styles.text}`}>Disable 2FA</span>
      </button>
    </div>
  );
}

export default SettingsMenu;
