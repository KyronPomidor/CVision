import styles from "./MetaItem.module.css";

function MetaItem({ icon, children }) {
    return(
        <span className={styles.item}>
            <img src={icon} alt="" className={styles.icon} />
            <span className={styles.text} >{children}</span>
        </span>
    )
}

export default MetaItem