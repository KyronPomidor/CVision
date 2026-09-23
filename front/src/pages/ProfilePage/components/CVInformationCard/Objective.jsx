import styles from "./Objective.module.css"
import typography from "../../../../Typography.module.css";

function Objective({ text }) {

    return (
        <div className={styles.objective}>
            <h2 className={typography.heading2}>Objective</h2>
            <p className={typography.mainText}>{text}</p>
        </div>
    )
}

export default Objective
