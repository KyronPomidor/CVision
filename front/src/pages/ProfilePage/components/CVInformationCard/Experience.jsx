import styles from "./Experience.module.css";
import typography from "../../../../Typography.module.css";

function formatMonthYear(dateString) {
  const date = new Date(dateString);
  return date.toLocaleDateString("en-US", { month: "long", year: "numeric" });
}

function Experience({ items = [] }) {
  return (
    <div className={styles.experience}>
      <h2 className={typography.heading2}>Experience</h2>

      <div className={styles.itemsList}>
        {items.map((item) => (
          <div key={item.id} className={styles.item}>
            <div className={styles.itemHeader}>
              <h3 className={typography.mainTextImportant}>{item.title}</h3>
              <p className={typography.comment}>
                {formatMonthYear(item.startDate)} - {formatMonthYear(item.endDate)}
              </p>
            </div>

            <p className={`${styles.itemCompany} ${typography.mainText}`}>
              {item.company}
              {item.location ? `, ${item.location}` : ""}
            </p>

            {item.description && <p className={typography.mainText}>{item.description}</p>}
          </div>
        ))}
      </div>
    </div>
  );
}

export default Experience;
