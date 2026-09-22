import styles from "./AdditionalInfo.module.css"

function AdditionalInfo({ text }) {

    return (
        <div className={styles.additionalInfo}>
            <h2 className={styles.heading}>Additional Information</h2>
            <p className={styles.text}>{text}</p>
        </div>
    )
}

export default AdditionalInfo
