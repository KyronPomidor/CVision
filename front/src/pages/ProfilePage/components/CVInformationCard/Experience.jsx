import { useEffect, useRef, useState } from "react";
import styles from "./Experience.module.css";
import menuStyles from "./TechnicalSkills.module.css";
import typography from "../../../../Typography.module.css";

function formatMonthYear(dateString) {
  if (!dateString) return "";
  const [year, month] = dateString.split("-").map(Number);
  return new Date(year, month - 1, 1).toLocaleDateString("en-US", {
    month: "long",
    year: "numeric",
  });
}

function Experience({ items = [], isEditing = false, onChange, saving }) {
  const [selected, setSelected] = useState(null);
  const [draft, setDraft] = useState(null);
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

  function editItem(item) {
    setDraft({
      ...item,
      title: item.title ?? "",
      company: item.company ?? "",
      location: item.location ?? "",
      description: item.description ?? "",
      startDate: item.startDate?.slice(0, 7) ?? "",
      endDate: item.endDate?.slice(0, 7) ?? "",
      current: !item.endDate,
    });
  }

  function saveItem(event) {
    event.preventDefault();
    if (!draft.title.trim()) return;
    const updatedItems = [...items];
    updatedItems[selected] = {
      id: draft.id,
      title: draft.title.trim(),
      company: draft.company.trim(),
      location: draft.location.trim(),
      description: draft.description.trim(),
      startDate: draft.startDate + "-01",
      endDate: draft.current ? null : draft.endDate + "-01",
    };
    onChange(updatedItems);
    setSelected(null);
  }

  function renderItem(item, index) {
    const open = selected === index;
    return (
      <div
        key={index}
        className={`${styles.item} ${isEditing && !saving ? styles.clickable : ""}`}
        ref={open ? menu : null}
        onClick={(event) => {
          if (!isEditing || saving || event.target.closest("button, form")) return;
          setSelected(index);
          setDraft(null);
        }}
      >
        {open && draft ? (
          <form className={styles.itemForm} onSubmit={saveItem}>
            <input
              className={`${styles.input} ${typography.mainTextImportant}`}
              aria-label="Job title"
              placeholder="Job title"
              required
              autoFocus
              value={draft.title}
              disabled={saving}
              onChange={(event) => setDraft({ ...draft, title: event.target.value })}
            />
            <div className={styles.dates}>
              <label className={typography.comment}>
                Start
                <input
                  className={styles.input}
                  type="month"
                  required
                  aria-label="Start month"
                  value={draft.startDate}
                  disabled={saving}
                  max={!draft.current ? draft.endDate || undefined : undefined}
                  onChange={(event) => setDraft({ ...draft, startDate: event.target.value })}
                />
              </label>
              <label className={typography.comment}>
                End
                <input
                  className={styles.input}
                  type="month"
                  aria-label="End month"
                  required={!draft.current}
                  disabled={saving || draft.current}
                  min={draft.startDate || undefined}
                  value={draft.current ? "" : draft.endDate}
                  onChange={(event) => setDraft({ ...draft, endDate: event.target.value })}
                />
              </label>
              <label className={typography.mainText}>
                <input
                  type="checkbox"
                  checked={draft.current}
                  disabled={saving}
                  onChange={(event) => setDraft({ ...draft, current: event.target.checked })}
                />
                I currently work here
              </label>
            </div>
            <input
              className={`${styles.input} ${styles.itemCompany} ${typography.mainText}`}
              aria-label="Company"
              placeholder="Company"
              value={draft.company}
              disabled={saving}
              onChange={(event) => setDraft({ ...draft, company: event.target.value })}
            />
            <input
              className={`${styles.input} ${styles.itemCompany} ${typography.mainText}`}
              aria-label="Work location"
              placeholder="Location"
              value={draft.location}
              disabled={saving}
              onChange={(event) => setDraft({ ...draft, location: event.target.value })}
            />
            <textarea
              className={`${styles.input} ${typography.mainText}`}
              aria-label="Experience description"
              placeholder="Description"
              rows={1}
              value={draft.description}
              disabled={saving}
              onChange={(event) => setDraft({ ...draft, description: event.target.value })}
            />
            <div className={menuStyles.menu}>
              <button
                type="button"
                aria-label="Close experience menu"
                disabled={saving}
                onClick={() => setSelected(null)}
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
            <div className={styles.itemHeader}>
              {isEditing ? (
                <button
                  className={`${styles.titleButton} ${typography.mainTextImportant}`}
                  type="button"
                  aria-expanded={open}
                  disabled={saving}
                  onClick={() => {
                    setSelected(index);
                    setDraft(null);
                  }}
                >
                  {item.title}
                </button>
              ) : (
                <h3 className={typography.mainTextImportant}>{item.title}</h3>
              )}
              <p className={typography.comment}>
                {formatMonthYear(item.startDate)} -{" "}
                {item.endDate ? formatMonthYear(item.endDate) : "Present"}
              </p>
            </div>
            <p className={`${styles.itemCompany} ${typography.mainText}`}>
              {item.company}
              {item.location ? `, ${item.location}` : ""}
            </p>
            {item.description && <p className={typography.mainText}>{item.description}</p>}
            {isEditing && open && (
              <div className={menuStyles.menu}>
                <button
                  type="button"
                  aria-label="Close experience menu"
                  disabled={saving}
                  onClick={() => setSelected(null)}
                >
                  ×
                </button>
                <button type="button" disabled={saving} onClick={() => editItem(item)}>
                  Edit
                </button>
                <button
                  type="button"
                  disabled={saving}
                  onClick={() => {
                    onChange(items.filter((_, itemIndex) => itemIndex !== index));
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
    <div className={styles.experience}>
      <h2 className={typography.heading2}>Experience</h2>
      <div className={styles.itemsList}>
        {items.map(renderItem)}
        {isEditing && selected === items.length && draft && renderItem(draft, items.length)}
        {isEditing && (
          <button
            className={styles.addButton}
            type="button"
            aria-label="Add experience"
            disabled={saving}
            onClick={() => {
              setSelected(items.length);
              editItem({ id: Math.max(0, ...items.map((item) => Number(item.id) || 0)) + 1 });
            }}
          >
            +
          </button>
        )}
      </div>
    </div>
  );
}

export default Experience;
