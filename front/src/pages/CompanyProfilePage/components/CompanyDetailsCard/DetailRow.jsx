import styles from "./DetailRow.module.css";

function DetailRow({ icon: Icon, label, value }) {
    return (
        <div className={styles.row}>
            <Icon className={styles.icon} aria-hidden="true" />
            <span className={styles.label}>{label}</span>
            <span className={styles.value}>{value}</span>
        </div>
    )
}

export default DetailRow