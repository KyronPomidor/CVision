import styles from "./Experience.module.css"

function Experience({ items = [] }) {

    return (
        <div className={styles.experience}>
            <h2 className={styles.heading}>Experience</h2>

            <div className={styles.itemsList}>
                {items.map((item) => (
                    <div key={item.id} className={styles.item}>
                        <div className={styles.itemHeader}>
                            <h3 className={styles.itemTitle}>{item.title}</h3>
                            <p className={styles.itemDates}>{item.startDate} - {item.endDate}</p>
                        </div>

                        <p className={styles.itemCompany}>
                            {item.company}{item.location ? `, ${item.location}` : ""}
                        </p>

                        {item.description && (
                            <p className={styles.itemDescription}>{item.description}</p>
                        )}
                    </div>
                ))}
            </div>
        </div>
    )
}

export default Experience
