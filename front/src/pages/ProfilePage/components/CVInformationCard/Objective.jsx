import styles from "./Objective.module.css";
import typography from "../../../../Typography.module.css";

function Objective({ text, isEditing, value, onChange, saving }) {
  return (
    <div className={styles.objective}>
      <h2 className={typography.heading2}>Objective</h2>
      {isEditing ? (
        <textarea
          className={`${styles.input} ${typography.mainText}`}
          aria-label="Objective"
          value={value}
          onChange={(event) => onChange(event.target.value)}
          maxLength={5000}
          rows={4}
          disabled={saving}
        />
      ) : (
        <p className={typography.mainText}>{text}</p>
      )}
    </div>
  );
}

export default Objective;
