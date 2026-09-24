import styles from "./AuthInput.module.css"

function AuthInput({ icon, options, ...inputProps }) {
    return (
        <label className={styles.field}>
            <img src={icon} alt="" className={styles.icon} />
            {options ? (
                <select {...inputProps} className={styles.select}>
                    {options.map((opt) => (
                        <option
                            key={opt.value}
                            value={opt.value}
                            disabled={opt.disabled}
                            hidden={opt.hidden}
                        >
                            {opt.label}
                        </option>
                    ))}
                </select>
            ) : (
                <input {...inputProps} />
            )}
        </label>
    )
}

export default AuthInput