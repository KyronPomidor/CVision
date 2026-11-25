import { useState } from "react";
import { errorMessage } from "../../api/client";
import typography from "../../Typography.module.css";
import { logout, enableTwoFactor, disableTwoFactor } from "../../api/auth";
import styles from "./SettingsMenu.module.css";

function SettingsMenu() {
  const [message, setMessage] = useState(null);
  return (
    <div className={styles.menu}>
      {message && <p role="status">{message}</p>}
      <button type="button" className={styles.menuItem} onClick={logout}>
        <span className={`${typography.mainTextImportant} ${styles.text}`}>Sign out</span>
      </button>
      <button
        type="button"
        className={styles.menuItem}
        onClick={() =>
          enableTwoFactor()
            .then(() => setMessage("2FA enabled."))
            .catch((err) => setMessage(errorMessage(err)))
        }
      >
        <span className={`${typography.mainTextImportant} ${styles.text}`}>Enable 2FA</span>
      </button>
      <button
        type="button"
        className={styles.menuItem}
        onClick={() =>
          disableTwoFactor()
            .then(() => setMessage("2FA disabled."))
            .catch((err) => setMessage(errorMessage(err)))
        }
      >
        <span className={`${typography.mainTextImportant} ${styles.text}`}>Disable 2FA</span>
      </button>
    </div>
  );
}

export default SettingsMenu;
