import styles from "./Experience.module.css"
import typography from "../../../../Typography.module.css";

function Experience({ items = [] }) {

    return (
        <div className={styles.experience}>
            <h2 className={typography.heading2}>Experience</h2>

            <div className={styles.itemsList}>
                {items.map((item) => (
                    <div key={item.id} className={styles.item}>
                        <div className={styles.itemHeader}>
                            <h3 className={typography.mainTextImportant}>{item.title}</h3>
                            <p className={typography.comment}>{item.startDate} - {item.endDate}</p>
                        </div>

                        <p className={styles.itemCompany}>
                            {item.company}{item.location ? `, ${item.location}` : ""}
                        </p>

                        {item.description && (
                            <p className={typography.mainText}>{item.description}</p>
                        )}
                    </div>
                ))}
            </div>
        </div>
    )
}

export default Experience
