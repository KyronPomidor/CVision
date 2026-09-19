import styles from "./DetailRow.module.css";

function DetailRow({ icon: icon, label, value }) {
    return (
        <div className={styles.row}>
            <img src={icon} className={styles.icon} />
            <span className={styles.label}>{label}</span>
            <span className={styles.value}>{value}</span>
        </div>
    )
}

export default DetailRow