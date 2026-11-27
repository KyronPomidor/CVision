import styles from "./TechnicalSkills.module.css";
import typography from "../../../../Typography.module.css";
import ItemMenu from "./ItemMenu";
import useItemMenu from "./useItemMenu";

function TechnicalSkills({ skills = [], editing = false, onChange, saving }) {
  const menu = useItemMenu();

  function save(event) {
    event.preventDefault();
    const name = menu.draft.trim().replace(/\s+/g, " ");
    const duplicate = skills.some(
      (skill, index) => index !== menu.selected && skill.toLowerCase() === name.toLowerCase(),
    );
    if (name && !duplicate) {
      const updated = [...skills];
      updated[menu.selected] = name;
      onChange(updated);
    }
    menu.close();
  }

  function renderSkill(skill, index) {
    const selected = menu.selected === index;
    const editingName = selected && menu.draft !== null;
    return (
      <div key={index} className={styles.skillWrapper} ref={selected ? menu.ref : null}>
        {editingName ? (
          <form onSubmit={save}>
            <input
              className={`${styles.skillPill} ${styles.input} ${typography.mainTextImportant}`}
              aria-label="Skill name"
              placeholder="Enter a skill"
              autoFocus
              maxLength={100}
              value={menu.draft}
              onChange={(event) => menu.setDraft(event.target.value)}
              disabled={saving}
            />
            <ItemMenu editing onClose={menu.close} disabled={saving} />
          </form>
        ) : (
          <>
            <button
              type="button"
              className={`${styles.skillPill} ${typography.mainTextImportant}`}
              aria-expanded={selected}
              onClick={() => menu.open(index)}
              disabled={saving}
            >
              {skill}
            </button>
            {selected && (
              <ItemMenu
                onClose={menu.close}
                onEdit={() => menu.setDraft(skill)}
                onDelete={() => {
                  onChange(skills.filter((_, i) => i !== index));
                  menu.close();
                }}
                disabled={saving}
              />
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
        {editing ? (
          <>
            {skills.map(renderSkill)}
            {menu.selected === skills.length && renderSkill("", skills.length)}
            <button
              type="button"
              className={styles.addButton}
              aria-label="Add skill"
              disabled={saving}
              onClick={() => {
                menu.open(skills.length);
                menu.setDraft("");
              }}
            >
              +
            </button>
          </>
        ) : (
          skills.map((skill, index) => (
            <span key={index} className={`${styles.skillPill} ${typography.mainTextImportant}`}>
              {skill}
            </span>
          ))
        )}
      </div>
    </div>
  );
}

export default TechnicalSkills;
