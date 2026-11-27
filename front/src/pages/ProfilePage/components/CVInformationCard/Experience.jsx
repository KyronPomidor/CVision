import styles from "./Experience.module.css";
import typography from "../../../../Typography.module.css";
import ItemMenu from "./ItemMenu";
import useItemMenu from "./useItemMenu";

function formatMonthYear(value) {
  if (!value) return "";
  const [year, month] = value.split("-").map(Number);
  return new Date(year, month - 1, 1).toLocaleDateString("en-US", {
    month: "long",
    year: "numeric",
  });
}

function Experience({ items = [], editing = false, onChange, saving }) {
  const menu = useItemMenu();

  function editItem(item) {
    menu.setDraft({
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

  function change(field, value) {
    menu.setDraft({ ...menu.draft, [field]: value });
  }

  function save(event) {
    event.preventDefault();
    const draft = menu.draft;
    const fields = new FormData(event.currentTarget);
    const startDate = fields.get("startDate");
    const endDate = fields.get("endDate");
    if (!draft.title.trim() || !startDate || (!draft.current && !endDate)) return;
    if (!draft.current && endDate < startDate) return;
    const updated = [...items];
    updated[menu.selected] = {
      id: draft.id,
      title: draft.title.trim(),
      company: draft.company.trim(),
      location: draft.location.trim(),
      description: draft.description.trim(),
      startDate: startDate + "-01",
      endDate: draft.current ? null : endDate + "-01",
    };
    onChange(updated);
    menu.close();
  }

  function renderItem(item, index) {
    const selected = menu.selected === index;
    const draft = selected ? menu.draft : null;
    return (
      <div
        key={index}
        ref={selected ? menu.ref : null}
        className={`${styles.item} ${editing && !saving ? styles.clickable : ""}`}
        onClick={(event) => {
          if (!editing || saving || draft || event.target.closest("button, form")) return;
          menu.open(index);
        }}
      >
        {draft ? (
          <form className={styles.itemForm} onSubmit={save}>
            <input
              className={`${styles.input} ${typography.mainTextImportant}`}
              aria-label="Job title"
              maxLength={255}
              placeholder="Job title"
              required
              autoFocus
              value={draft.title}
              onChange={(event) => change("title", event.target.value)}
              disabled={saving}
            />
            <div className={styles.dates}>
              <label className={typography.comment}>
                Start
                <input
                  className={styles.input}
                  type="month"
                  aria-label="Start month"
                  name="startDate"
                  required
                  value={draft.startDate}
                  disabled={saving}
                  max={!draft.current ? draft.endDate || undefined : undefined}
                  onChange={(event) => change("startDate", event.target.value)}
                />
              </label>
              <label className={typography.comment}>
                End
                <input
                  className={styles.input}
                  type="month"
                  aria-label="End month"
                  name="endDate"
                  required={!draft.current}
                  disabled={saving || draft.current}
                  min={draft.startDate || undefined}
                  value={draft.current ? "" : draft.endDate}
                  onChange={(event) => change("endDate", event.target.value)}
                />
              </label>
              <label className={typography.mainText}>
                <input
                  type="checkbox"
                  checked={draft.current}
                  disabled={saving}
                  onChange={(event) => change("current", event.target.checked)}
                />{" "}
                I currently work here
              </label>
            </div>
            <input
              className={`${styles.input} ${styles.itemCompany} ${typography.mainText}`}
              aria-label="Company"
              maxLength={255}
              placeholder="Company"
              value={draft.company}
              onChange={(event) => change("company", event.target.value)}
              disabled={saving}
            />
            <input
              className={`${styles.input} ${styles.itemCompany} ${typography.mainText}`}
              aria-label="Work location"
              maxLength={255}
              placeholder="Location"
              value={draft.location}
              onChange={(event) => change("location", event.target.value)}
              disabled={saving}
            />
            <textarea
              className={`${styles.input} ${typography.mainText}`}
              aria-label="Experience description"
              maxLength={5000}
              placeholder="Description"
              rows={1}
              value={draft.description}
              disabled={saving}
              onChange={(event) => change("description", event.target.value)}
            />
            <ItemMenu editing onClose={menu.close} disabled={saving} />
          </form>
        ) : (
          <>
            <div className={styles.itemHeader}>
              {editing ? (
                <button
                  type="button"
                  className={`${styles.titleButton} ${typography.mainTextImportant}`}
                  aria-expanded={selected}
                  disabled={saving}
                  onClick={() => menu.open(index)}
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
            {editing && selected && (
              <ItemMenu
                onClose={menu.close}
                onEdit={() => editItem(item)}
                onDelete={() => {
                  onChange(items.filter((_, i) => i !== index));
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
    <div className={styles.experience}>
      <h2 className={typography.heading2}>Experience</h2>
      <div className={styles.itemsList}>
        {items.map(renderItem)}
        {editing &&
          menu.selected === items.length &&
          menu.draft &&
          renderItem(menu.draft, items.length)}
        {editing && (
          <button
            type="button"
            className={styles.addButton}
            aria-label="Add experience"
            disabled={saving}
            onClick={() => {
              menu.open(items.length);
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
