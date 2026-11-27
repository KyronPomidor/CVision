import { useState } from "react";
import { errorMessage } from "../../../../api/client";
import styles from "./JobDescription.module.css";
import typography from "../../../../Typography.module.css";
import { skillLabel } from "../../../../components/skillLabel";
import Star from "../../../../assets/star-logo.svg?react";

function JobDescription({
  className = "",
  skills,
  onSaveYears,
  description,
  onApply,
  applying,
  applied,
  ...props
}) {
  const [editingId, setEditingId] = useState(null);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  async function saveYears(event) {
    event.preventDefault();
    const years = Number(new FormData(event.currentTarget).get("years"));
    setSaving(true);
    setError("");
    try {
      await onSaveYears(editingId, years);
      setEditingId(null);
    } catch (err) {
      setError(errorMessage(err));
    } finally {
      setSaving(false);
    }
  }
  return (
    <div className={`${className} ${styles.container}`} {...props}>
      <h2 className={typography.heading2}>Job Description</h2>
      <p className={`${styles.body} ${typography.mainText}`}>{description}</p>
      <div className={styles.skillSection}>
        <span className={`${typography.mainTextImportant} ${styles.subTitle}`}>
          Necessary Skills (AI extracted)
        </span>
        <div className={styles.skillsRow}>
          {skills.map((skill) => onSaveYears && editingId === skill.id ? (
            <form key={skill.id} className={styles.skillPill} onSubmit={saveYears}>
              <label>{skill.name} · Required years{" "}
                <input name="years" type="number" min="0" max="60" step="1" required
                  defaultValue={skill.yearsOfExperience ?? 0} autoFocus disabled={saving} />
              </label>
              <button disabled={saving}>Save</button>
              <button type="button" disabled={saving} onClick={() => { setEditingId(null); setError(""); }}>Cancel</button>
            </form>
          ) : onSaveYears ? (
            <button key={skill.id} type="button" disabled={saving}
              className={`${typography.mainTextImportant} ${styles.skillPill}`}
              aria-label={`Edit required years for ${skill.name}`}
              onClick={() => { setEditingId(skill.id); setError(""); }}>
              {skillLabel(skill.name, skill.yearsOfExperience)}
            </button>
          ) : (
            <span key={skill.id ?? skill.name} className={`${typography.mainTextImportant} ${styles.skillPill}`}>
              {skillLabel(skill.name, skill.yearsOfExperience)}
            </span>
          ))}
        </div>
      </div>
      {error && <p role="alert">{error}</p>}
      <div className={styles.buttonRow}>
        {onApply && (
          <button
            className={`${typography.heading2} ${styles.apply}`}
            onClick={onApply}
            disabled={applying || applied}
          >
            {applying ? "Applying..." : applied ? "Unavailable" : "Apply"}
          </button>
        )}
        <button
          type="button"
          className={styles.favorite}
          disabled
          title="Saved jobs are not supported yet"
          aria-pressed={false}
          aria-label="Saved jobs are not supported yet"
        >
          <Star className={styles.starLogo} />
        </button>
      </div>
    </div>
  );
}

export default JobDescription;
