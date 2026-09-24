import { useState } from "react"
import { Link } from "react-router-dom"
import AuthLayout from "../../components/AuthLayout/AuthLayout"
import AuthInput from "../../components/AuthLayout/AuthInput"
import mailIcon from "../../assets/mail-icon.svg"
import lockIcon from "../../assets/lock-icon.svg"
import styles from "./LoginPage.module.css"

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
                <h1 className={styles.title}>Welcome to CVision</h1>

                <AuthInput
                    icon={mailIcon}
                    type="email"
                    placeholder="Email address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <AuthInput
                    icon={lockIcon}
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <button type="submit" className={styles.submitButton}>
                    Login
                </button>

                <p className={styles.switchText}>
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
