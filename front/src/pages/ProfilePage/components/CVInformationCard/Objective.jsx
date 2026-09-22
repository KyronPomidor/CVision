import styles from "./Objective.module.css"

function Objective({ text }) {

    return (
        <div className={styles.objective}>
            <h2 className={styles.heading}>Objective</h2>
            <p className={styles.text}>{text}</p>
        </div>
    )
}

export default Objective
