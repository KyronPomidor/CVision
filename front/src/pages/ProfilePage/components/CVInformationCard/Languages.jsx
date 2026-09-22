import styles from "./Languages.module.css"

const LEVELS = ["A1", "A2", "B1", "B2", "C1", "C2", "Native"];

function Languages({ languages = [] }) {

    return (
        <div className={styles.languages}>
            <h2 className={styles.heading}>Languages</h2>

            <div className={styles.languagesList}>
                {languages.map((language) => (
                    <div key={language.name} className={styles.languageRow}>
                        <p className={styles.languageName}>{language.name}</p>

                        <div className={styles.levelsList}>
                            {LEVELS.map((level) => (
                                <span
                                    key={level}
                                    className={
                                        level === language.level
                                            ? `${styles.levelPill} ${styles.levelPillActive}`
                                            : styles.levelPill
                                    }
                                >
                                    {level}
                                </span>
                            ))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default Languages
