import { useEffect, useRef, useState } from "react";
import styles from "./Languages.module.css";
import menuStyles from "./TechnicalSkills.module.css";
import typography from "../../../../Typography.module.css";

const LEVELS = ["A1", "A2", "B1", "B2", "C1", "C2", "Native"];

function Languages({ languages = [], isEditing = false, onChange, saving }) {
  const [selected, setSelected] = useState(null);
  const [editing, setEditing] = useState(false);
  const [name, setName] = useState("");
  const [newLevel, setNewLevel] = useState("A1");
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

  function saveLanguage(event) {
    event.preventDefault();
    const value = name.trim().replace(/\s+/g, " ");
    const duplicate = languages.some(
      (language, index) =>
        index !== selected && language.name.trim().toLowerCase() === value.toLowerCase(),
    );

    if (value && !duplicate) {
      const updatedLanguages = [...languages];
      updatedLanguages[selected] = {
        ...languages[selected],
        name: value,
        level: languages[selected]?.level ?? newLevel,
      };
      onChange(updatedLanguages);
    }
    setSelected(null);
  }

  function renderLanguage(language, index) {
    const open = selected === index;
    return (
      <div key={index} className={styles.languageRow} ref={open ? menu : null}>
        <div className={menuStyles.skillWrapper}>
          {isEditing ? (
            open && editing ? (
              <form onSubmit={saveLanguage}>
                <input
                  className={`${styles.nameInput} ${typography.mainTextImportant}`}
                  aria-label="Language name"
                  placeholder="Enter a language"
                  value={name}
                  onChange={(event) => setName(event.target.value)}
                  maxLength={100}
                  autoFocus
                  disabled={saving}
                />
                <div className={menuStyles.menu}>
                  <button
                    type="button"
                    aria-label="Close language menu"
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
                  className={`${styles.nameButton} ${typography.mainTextImportant}`}
                  aria-expanded={open}
                  disabled={saving}
                  onClick={() => {
                    setSelected(index);
                    setEditing(false);
                  }}
                >
                  {language.name}
                </button>
                {open && (
                  <div className={menuStyles.menu}>
                    <button
                      type="button"
                      aria-label="Close language menu"
                      onClick={() => setSelected(null)}
                      disabled={saving}
                    >
                      ×
                    </button>
                    <button
                      type="button"
                      disabled={saving}
                      onClick={() => {
                        setName(language.name);
                        setEditing(true);
                      }}
                    >
                      Edit
                    </button>
                    <button
                      type="button"
                      disabled={saving}
                      onClick={() => {
                        onChange(languages.filter((_, languageIndex) => languageIndex !== index));
                        setSelected(null);
                      }}
                    >
                      Delete
                    </button>
                  </div>
                )}
              </>
            )
          ) : (
            <p className={`${styles.languageName} ${typography.mainTextImportant}`}>
              {language.name}
            </p>
          )}
        </div>
        <div className={styles.levelsList}>
          {LEVELS.map((level) => {
            const className = `${styles.levelPill} ${level === language.level ? styles.levelPillActive : ""} ${typography.mainTextImportant}`;
            return isEditing ? (
              <button
                type="button"
                key={level}
                className={className}
                aria-label={`${language.name || "New language"} ${level}`}
                aria-pressed={level === language.level}
                disabled={saving}
                onClick={() => {
                  if (index === languages.length) {
                    setNewLevel(level);
                  } else {
                    onChange(
                      languages.map((item, languageIndex) =>
                        languageIndex === index ? { ...item, level } : item,
                      ),
                    );
                  }
                }}
              >
                {level}
              </button>
            ) : (
              <span key={level} className={className}>
                {level}
              </span>
            );
          })}
        </div>
      </div>
    );
  }

  return (
    <div className={styles.languages}>
      <h2 className={typography.heading2}>Languages</h2>
      <div className={styles.languagesList}>
        {languages.map(renderLanguage)}
        {isEditing &&
          selected === languages.length &&
          renderLanguage({ name: "", level: newLevel }, languages.length)}
        {isEditing && (
          <button
            type="button"
            className={styles.addButton}
            aria-label="Add language"
            disabled={saving}
            onClick={() => {
              setSelected(languages.length);
              setEditing(true);
              setName("");
              setNewLevel("A1");
            }}
          >
            +
          </button>
        )}
      </div>
    </div>
  );
}

export default Languages;
