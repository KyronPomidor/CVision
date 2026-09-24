import styles from "./MetaItem.module.css";
import typography from "../../Typography.module.css";

function MetaItem({ icon, children }) {
  return (
    <span className={styles.item}>
      <img src={icon} alt="" className={styles.icon} />
      <span className={typography.comment}>{children}</span>
    </span>
  );
}

export default MetaItem;
