import { useState } from "react"
import { Link } from "react-router-dom"
import AuthLayout from "../../components/AuthLayout/AuthLayout"
import AuthInput from "../../components/AuthLayout/AuthInput"
import mailIcon from "../../assets/mail-icon.svg"
import styles from "./styles/ForgotPasswordPage.module.css"

function ForgotPasswordPage() {
    const [email, setEmail] = useState("")
    const [submitted, setSubmitted] = useState(false)

    function handleSubmit(e) {
        e.preventDefault()
        // TODO: call forgot password API
        setSubmitted(true)
    }

    return (
        <AuthLayout>
            <form className={styles.card} onSubmit={handleSubmit}>
                <h1 className={styles.title}>Forgot Password?</h1>
                <p className={styles.subtitle}>
                    Enter your email address and we'll send you a link to reset your password.
                </p>

                <AuthInput
                    icon={mailIcon}
                    type="email"
                    placeholder="Email address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <button type="submit" className={styles.submitButton}>
                    Send Reset Link
                </button>

                {submitted && (
                    <p className={styles.confirmation}>
                        If an account exists for {email}, a reset link has been sent.
                    </p>
                )}

                <p className={styles.switchText}>
                    Remembered your password?{" "}
                    <Link to="/login" className={styles.switchLink}>
                        Log In Now
                    </Link>
                </p>
            </form>
        </AuthLayout>
    )
}

export default ForgotPasswordPage
