import styles from "./Languages.module.css"
import typography from "../../../../Typography.module.css";

const LEVELS = ["A1", "A2", "B1", "B2", "C1", "C2", "Native"];

function Languages({ languages = [] }) {

    return (
        <div className={styles.languages}>
            <h2 className={typography.heading2}>Languages</h2>

            <div className={styles.languagesList}>
                {languages.map((language) => (
                    <div key={language.name} className={styles.languageRow}>
                        <p className={`${styles.languageName} ${typography.mainTextImportant}`}>{language.name}</p>

                        <div className={styles.levelsList}>
                            {LEVELS.map((level) => (
                                <span
                                    key={level}
                                    className={
                                        level === language.level
                                            ? `${styles.levelPill} ${styles.levelPillActive} ${typography.mainTextImportant}`
                                            : `${styles.levelPill} ${typography.mainTextImportant}`
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
