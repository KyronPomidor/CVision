import styles from "./TechnicalSkills.module.css"

function TechnicalSkills({ skills = [] }) {

    return (
        <div className={styles.technicalSkills}>
            <h2 className={styles.heading}>Technical Skills</h2>

            <div className={styles.skillsList}>
                {skills.map((skill) => (
                    <span key={skill} className={styles.skillPill}>
                        {skill}
                    </span>
                ))}
            </div>
        </div>
    )
}

export default TechnicalSkills
