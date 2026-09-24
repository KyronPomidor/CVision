import { useState } from "react"
import { Link } from "react-router-dom"
import AuthLayout from "../../components/AuthLayout/AuthLayout"
import AuthInput from "../../components/AuthLayout/AuthInput"
import MailLogo from "../../assets/mail-icon.svg?react"
import LockLogo from "../../assets/lock-icon.svg?react"
import typography from "../../Typography.module.css"
import styles from "../../components/AuthLayout/AuthCard.module.css"

function LoginPage() {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    function handleSubmit(e) {
        e.preventDefault()
        // TODO: call login API
    }

    return (
        <AuthLayout>
            <form className={styles.card} onSubmit={handleSubmit}>
                <h1 className={`${typography.heading1} ${styles.title}`}>
                    Welcome to CVision
                </h1>

                <AuthInput
                    icon={MailLogo}
                    type="email"
                    placeholder="Email address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <AuthInput
                    icon={LockLogo}
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <button
                    type="submit"
                    className={`${typography.mainTextImportant} ${styles.submitButton}`}
                >
                    Login
                </button>

                <p className={`${typography.mainTextImportant} ${styles.switchText}`}>
                    Don't have an account?{" "}
                    <Link to="/signup" className={styles.switchLink}>
                        Sign Up Now
                    </Link>
                </p>
            </form>
        </AuthLayout>
    )
}

export default LoginPage