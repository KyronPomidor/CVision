import styles from "./ItemMenu.module.css";

function ItemMenu({ editing, onClose, onEdit, onDelete, onSave, disabled }) {
  return (
    <div className={styles.menu} aria-label="Item options">
      <button type="button" aria-label="Close item menu" onClick={onClose} disabled={disabled}>
        �
      </button>
      {editing ? (
        <button type={onSave ? "button" : "submit"} onClick={onSave} disabled={disabled}>
          Save
        </button>
      ) : (
        <>
          <button type="button" onClick={onEdit} disabled={disabled}>
            Edit
          </button>
          <button type="button" onClick={onDelete} disabled={disabled}>
            Delete
          </button>
        </>
      )}
    </div>
  );
}

export default ItemMenu;
