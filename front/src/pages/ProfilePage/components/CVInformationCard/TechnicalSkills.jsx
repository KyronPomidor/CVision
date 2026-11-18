import { useEffect, useRef, useState } from "react";
import styles from "./TechnicalSkills.module.css";
import typography from "../../../../Typography.module.css";

function TechnicalSkills({ skills = [], isEditing = false, onChange, saving }) {
  const [selected, setSelected] = useState(null);
  const [editing, setEditing] = useState(false);
  const [value, setValue] = useState("");
  const menu = useRef(null);

  useEffect(() => {
    if (selected === null) return;

    function closeMenu(event) {
      if (!menu.current?.contains(event.target)) setSelected(null);
    }

    function closeOnEscape(event) {
      if (event.key === "Escape") setSelected(null);
    }

    document.addEventListener("pointerdown", closeMenu);
    document.addEventListener("keydown", closeOnEscape);
    return () => {
      document.removeEventListener("pointerdown", closeMenu);
      document.removeEventListener("keydown", closeOnEscape);
    };
  }, [selected]);

  function saveSkill(event) {
    event.preventDefault();
    const name = value.trim().replace(/\s+/g, " ");
    const duplicate = skills.some(
      (skill, index) => index !== selected && skill.trim().toLowerCase() === name.toLowerCase(),
    );

    if (name && !duplicate) {
      const updatedSkills = [...skills];
      updatedSkills[selected] = name;
      onChange(updatedSkills);
    }
    setSelected(null);
  }

  function renderSkill(skill, index) {
    const open = selected === index;
    return (
      <div key={index} className={styles.skillWrapper} ref={open ? menu : null}>
        {open && editing ? (
          <form onSubmit={saveSkill}>
            <input
              className={`${styles.skillPill} ${styles.skillInput} ${typography.mainTextImportant}`}
              aria-label="Skill name"
              placeholder="Enter a skill"
              value={value}
              onChange={(event) => setValue(event.target.value)}
              maxLength={100}
              autoFocus
              disabled={saving}
            />
            <div className={styles.menu}>
              <button
                type="button"
                aria-label="Close skill menu"
                onClick={() => setSelected(null)}
                disabled={saving}
              >
                ×
              </button>
              <button type="submit" disabled={saving}>
                Save
              </button>
            </div>
          </form>
        ) : (
          <>
            <button
              type="button"
              className={`${styles.skillPill} ${typography.mainTextImportant}`}
              disabled={saving}
              aria-expanded={open}
              onClick={() => {
                setSelected(index);
                setEditing(false);
              }}
            >
              {skill}
            </button>
            {open && (
              <div className={styles.menu}>
                <button
                  type="button"
                  aria-label="Close skill menu"
                  onClick={() => setSelected(null)}
                >
                  ×
                </button>
                <button
                  type="button"
                  disabled={saving}
                  onClick={() => {
                    setValue(skill);
                    setEditing(true);
                  }}
                >
                  Edit
                </button>
                <button
                  type="button"
                  disabled={saving}
                  onClick={() => {
                    onChange(skills.filter((_, skillIndex) => skillIndex !== index));
                    setSelected(null);
                  }}
                >
                  Delete
                </button>
              </div>
            )}
          </>
        )}
      </div>
    );
  }

  return (
    <div className={styles.technicalSkills}>
      <h2 className={typography.heading2}>Technical Skills</h2>
      <div className={styles.skillsList}>
        {isEditing ? (
          <>
            {skills.map(renderSkill)}
            {selected === skills.length && renderSkill("", skills.length)}
            <button
              type="button"
              className={`${styles.skillPill} ${styles.addButton} ${typography.mainTextImportant}`}
              aria-label="Add skill"
              disabled={saving}
              onClick={() => {
                setSelected(skills.length);
                setEditing(true);
                setValue("");
              }}
            >
              +
            </button>
          </>
        ) : (
          skills.map((skill) => (
            <span key={skill} className={`${styles.skillPill} ${typography.mainTextImportant}`}>
              {skill}
            </span>
          ))
        )}
      </div>
    </div>
  );
}

export default TechnicalSkills;
