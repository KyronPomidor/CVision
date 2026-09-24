import styles from "./QuickFilters.module.css";
import typography from "../../../../Typography.module.css";
import FilterLogo from "./assets/filter-logo.svg?react";

function QuickFilters({ groups, onChange }) {
  return (
    <div className={styles.quickFilters}>
      <div className={styles.header}>
        <FilterLogo className={styles.headerIcon} />
        <h2 className={typography.heading2}>Quick Filters</h2>
      </div>

      <div className={styles.groupsList}>
        {groups.map((group) => (
          <div key={group.title} className={styles.group}>
            <h3 className={typography.mainTextImportant}>{group.title}</h3>

            <div className={styles.optionsList}>
              {group.options.map((option) => (
                <label key={option} className={`${typography.mainText} ${styles.option}`}>
                  <input
                    type="checkbox"
                    value={option}
                    onChange={(e) => onChange?.(group.title, option, e.target.checked)}
                    className={styles.checkboxInput}
                  />
                  <span>{option}</span>
                </label>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default QuickFilters;
