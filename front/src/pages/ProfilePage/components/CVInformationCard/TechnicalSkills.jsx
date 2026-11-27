import { useImperativeHandle, useRef } from "react";
import styles from "./TechnicalSkills.module.css";
import typography from "../../../../Typography.module.css";
import { skillLabel } from "../../../../components/skillLabel";
import ItemMenu from "./ItemMenu";
import useItemMenu from "./useItemMenu";

function TechnicalSkills({ skills = [], editing = false, onChange, saving, editorRef }) {
  const menu = useItemMenu(true);
  const form = useRef(null);

  function readSkills() {
    if (!form.current) return skills;
    if (!form.current.reportValidity()) return null;
    const name = menu.draft.name.trim().replace(/\s+/g, " ");
    const duplicate = skills.some(
      (skill, index) => index !== menu.selected && skill.name.toLowerCase() === name.toLowerCase(),
    );
    if (!name || duplicate) {
      const input = form.current.elements.namedItem("skillName");
      input.setCustomValidity(duplicate ? "This skill is already listed." : "Enter a skill name.");
      input.reportValidity();
      return null;
    }
    const updated = [...skills];
    updated[menu.selected] = { name, yearsOfExperience: menu.draft.yearsOfExperience == null || menu.draft.yearsOfExperience === "" ? null : Number(menu.draft.yearsOfExperience) };
    return updated;
  }

  useImperativeHandle(editorRef, () => ({ readSkills }));

  function save(event) {
    event.preventDefault();
    const updated = readSkills();
    if (!updated) return;
    onChange(updated);
    menu.close();
  }

  function renderSkill(skill, index) {
    const selected = menu.selected === index;
    const editingName = selected && menu.draft !== null;
    return (
      <div key={index} className={styles.skillWrapper} ref={selected ? menu.ref : null}>
        {editingName ? (
          <form ref={form} className={styles.editor} onSubmit={save}>
            <input
              className={`${styles.skillPill} ${styles.input} ${typography.mainTextImportant}`}
              name="skillName"
              onInput={(event) => event.target.setCustomValidity("")}
              aria-label="Skill name"
              placeholder="Enter a skill"
              autoFocus
              maxLength={100}
              required
              value={menu.draft.name}
              onChange={(event) => menu.setDraft({ ...menu.draft, name: event.target.value })}
              disabled={saving}
            />
            <label className={styles.yearsField}>
              Years
              <input
                type="number"
                aria-label="Years of experience"
                min="0"
                max="60"
                step="1"
                value={menu.draft.yearsOfExperience ?? ""}
                onChange={(event) => menu.setDraft({ ...menu.draft, yearsOfExperience: event.target.value })}
                disabled={saving}
              />
            </label>
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
              {skillLabel(skill.name, skill.yearsOfExperience)}
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
                menu.setDraft({ name: "", yearsOfExperience: null });
              }}
            >
              +
            </button>
          </>
        ) : (
          skills.map((skill, index) => (
            <span key={index} className={`${styles.skillPill} ${typography.mainTextImportant}`}>
              {skillLabel(skill.name, skill.yearsOfExperience)}
            </span>
          ))
        )}
      </div>
    </div>
  );
}

export default TechnicalSkills;
