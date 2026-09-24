import styles from "./AuthInput.module.css";
import typography from "../../Typography.module.css";

function AuthInput({ icon: Icon, options, ...inputProps }) {
  return (
    <label className={styles.field}>
      <Icon className={styles.icon} />
      {options ? (
        <select {...inputProps} className={`${typography.mainTextImportant} ${styles.select}`}>
          {options.map((opt) => (
            <option key={opt.value} value={opt.value} disabled={opt.disabled} hidden={opt.hidden}>
              {opt.label}
            </option>
          ))}
        </select>
      ) : (
        <input {...inputProps} className={typography.mainTextImportant} />
      )}
    </label>
  );
}

export default AuthInput;
