import styles from "./AdditionalInfo.module.css"
import typography from "../../../../Typography.module.css";

function AdditionalInfo({ text }) {

    return (
        <div className={styles.additionalInfo}>
            <h2 className={typography.heading2}>Additional Information</h2>
            <p className={typography.mainText}>{text}</p>
        </div>
    )
}

export default AdditionalInfo
