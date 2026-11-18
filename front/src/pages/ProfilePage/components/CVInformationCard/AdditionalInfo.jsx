import styles from "./AdditionalInfo.module.css";
import typography from "../../../../Typography.module.css";

function AdditionalInfo({ text, isEditing, value, onChange, saving }) {
  return (
    <div className={styles.additionalInfo}>
      <h2 className={typography.heading2}>Additional Information</h2>
      {isEditing ? (
        <textarea
          className={`${styles.input} ${typography.mainText}`}
          aria-label="Additional Information"
          value={value}
          onChange={(event) => onChange(event.target.value)}
          maxLength={5000}
          rows={1}
          disabled={saving}
        />
      ) : (
        <p className={typography.mainText}>{text}</p>
      )}
    </div>
  );
}

export default AdditionalInfo;
