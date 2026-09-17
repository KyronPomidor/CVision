import styles from "./styles/AuthInput.module.css"

function AuthInput({ icon, ...inputProps }) {
    return (
        <label className={styles.field}>
            <img src={icon} alt="" className={styles.icon} />
            <input {...inputProps} />
        </label>
    )
}

export default AuthInput
