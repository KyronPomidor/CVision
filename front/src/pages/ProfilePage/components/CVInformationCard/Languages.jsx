import styles from "./Languages.module.css";
import typography from "../../../../Typography.module.css";
import ItemMenu from "./ItemMenu";
import useItemMenu from "./useItemMenu";
import { useState } from "react";

const LEVELS = ["A1", "A2", "B1", "B2", "C1", "C2", "Native"];

function Languages({ languages = [], editing = false, onChange, saving }) {
  const menu = useItemMenu();
  const [newLevel, setNewLevel] = useState("A1");

  function save(event) {
    event.preventDefault();
    const name = menu.draft.trim().replace(/\s+/g, " ");
    const duplicate = languages.some(
      (item, index) => index !== menu.selected && item.name.toLowerCase() === name.toLowerCase(),
    );
    if (name && !duplicate) {
      const updated = [...languages];
      updated[menu.selected] = { name, level: languages[menu.selected]?.level ?? newLevel };
      onChange(updated);
    }
    menu.close();
  }

  function renderLanguage(language, index) {
    const selected = menu.selected === index;
    return (
      <div key={index} className={styles.languageRow} ref={selected ? menu.ref : null}>
        <div className={styles.nameWrapper}>
          {editing ? (
            selected && menu.draft !== null ? (
              <form onSubmit={save}>
                <input
                  className={`${styles.nameInput} ${typography.mainTextImportant}`}
                  aria-label="Language name"
                  placeholder="Enter a language"
                  value={menu.draft}
                  onChange={(event) => menu.setDraft(event.target.value)}
                  maxLength={100}
                  autoFocus
                  disabled={saving}
                />
                <ItemMenu editing onClose={menu.close} disabled={saving} />
              </form>
            ) : (
              <>
                <button
                  type="button"
                  className={`${styles.nameButton} ${typography.mainTextImportant}`}
                  aria-expanded={selected}
                  disabled={saving}
                  onClick={() => menu.open(index)}
                >
                  {language.name}
                </button>
                {selected && (
                  <ItemMenu
                    onClose={menu.close}
                    onEdit={() => menu.setDraft(language.name)}
                    onDelete={() => {
                      onChange(languages.filter((_, i) => i !== index));
                      menu.close();
                    }}
                    disabled={saving}
                  />
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
            return editing ? (
              <button
                type="button"
                key={level}
                className={className}
                disabled={saving}
                aria-label={`${language.name || "New language"} ${level}`}
                aria-pressed={level === language.level}
                onClick={() => {
                  if (index === languages.length) setNewLevel(level);
                  else
                    onChange(languages.map((item, i) => (i === index ? { ...item, level } : item)));
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
        {editing &&
          menu.selected === languages.length &&
          renderLanguage({ name: "", level: newLevel }, languages.length)}
        {editing && (
          <button
            type="button"
            className={styles.addButton}
            aria-label="Add language"
            disabled={saving}
            onClick={() => {
              menu.open(languages.length);
              menu.setDraft("");
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
