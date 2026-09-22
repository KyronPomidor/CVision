import styles from "./DetailRow.module.css";
import typography from "../../../../Typography.module.css"

function DetailRow({ icon: Icon, label, value }) {
  return (
    <div className={styles.row}>
      <Icon className={styles.icon} aria-hidden="true" />
      <span className={typography.miniComments}>{label}</span>
      <span className={`${typography.miniComments} ${styles.value}`}>{value}</span>
    </div>
  );
}

export default DetailRow;
