import styles from "./SortBy.module.css";
import typography from "../../../../Typography.module.css";
import SortLogo from "./assets/sort-logo.svg?react";

function SortBy({ options, onChange }) {
  return (
    <div className={styles.sortBy}>
      <div className={styles.header}>
        <SortLogo className={styles.headerIcon} />
        <h2 className={typography.heading2}>Sort by</h2>
      </div>

      <div className={styles.optionsList}>
        {options.map((option) => (
          <label key={option} className={`${typography.mainText} ${styles.option}`}>
            <input
              type="radio"
              name="sortBy"
              value={option}
              onChange={() => onChange?.(option)}
              className={styles.radioInput}
            />
            <span>{option}</span>
          </label>
        ))}
      </div>
    </div>
  );
}

export default SortBy;
