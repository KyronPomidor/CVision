import styles from "./Objective.module.css";
import typography from "../../../../Typography.module.css";

function Objective({ text, editing, value, onChange, saving }) {
  return (
    <div className={styles.objective}>
      <h2 className={typography.heading2}>Objective</h2>
      {editing ? (
        <textarea
          className={`${styles.input} ${typography.mainText}`}
          aria-label="Objective"
          placeholder="Describe your career objective"
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

export default Objective;
