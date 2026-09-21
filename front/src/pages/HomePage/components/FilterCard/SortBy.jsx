import styles from "./styles/SortBy.module.css"
import sortLogo from "../../../../assets/sort-logo.svg";

function SortBy({ options, onChange }) {

    return (
        <div className={styles.sortBy}>
            <div className={styles.header}>
                <img src={sortLogo} alt="sortLogo" className={styles.headerIcon} />
                <h2 className={styles.headerTitle}>Sort by</h2>
            </div>

            <div className={styles.optionsList}>
                {options.map((option) => (
                    <label key={option} className={styles.option}>
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
    )
}

export default SortBy
