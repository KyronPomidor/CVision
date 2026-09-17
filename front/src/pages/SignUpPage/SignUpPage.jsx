import { useState } from "react"
import { Link } from "react-router-dom"
import AuthLayout from "../../components/AuthLayout/AuthLayout"
import AuthInput from "../../components/AuthLayout/AuthInput"
import userIcon from "../../assets/user-icon.svg"
import mailIcon from "../../assets/mail-icon.svg"
import phoneIcon from "../../assets/phone-icon.svg"
import lockIcon from "../../assets/lock-icon.svg"
import styles from "./styles/SignUpPage.module.css"

function SignUpPage() {
    const [form, setForm] = useState({
        fullName: "",
        email: "",
        phone: "",
        password: "",
        confirmPassword: "",
    })
    const [agreed, setAgreed] = useState(false)

    function updateField(field) {
        return (e) => setForm((prev) => ({ ...prev, [field]: e.target.value }))
    }

    function handleSubmit(e) {
        e.preventDefault()
        // TODO: call sign up API
    }

    return (
        <AuthLayout mapOffset={-390}>
            <form className={styles.card} onSubmit={handleSubmit}>
                <h1 className={styles.title}>Create CVision Account</h1>

                <AuthInput
                    icon={userIcon}
                    type="text"
                    placeholder="Full Name"
                    value={form.fullName}
                    onChange={updateField("fullName")}
                    required
                />

                <AuthInput
                    icon={mailIcon}
                    type="email"
                    placeholder="Email Address"
                    value={form.email}
                    onChange={updateField("email")}
                    required
                />

                <AuthInput
                    icon={phoneIcon}
                    type="tel"
                    placeholder="Phone Number"
                    value={form.phone}
                    onChange={updateField("phone")}
                    required
                />

                <AuthInput
                    icon={lockIcon}
                    type="password"
                    placeholder="Password"
                    value={form.password}
                    onChange={updateField("password")}
                    required
                />

                <AuthInput
                    icon={lockIcon}
                    type="password"
                    placeholder="Confirm Password"
                    value={form.confirmPassword}
                    onChange={updateField("confirmPassword")}
                    required
                />

                <label className={styles.agreement}>
                    <input
                        type="checkbox"
                        checked={agreed}
                        onChange={(e) => setAgreed(e.target.checked)}
                        required
                    />
                    <span>
                        I agree to the{" "}
                        <Link to="/terms-of-service">Terms of Service</Link> and{" "}
                        <Link to="/privacy-policy">Privacy Policy</Link>
                    </span>
                </label>

                <button type="submit" className={styles.submitButton}>
                    Sign Up
                </button>

                <p className={styles.switchText}>
                    Already have an account?{" "}
                    <Link to="/login" className={styles.switchLink}>
                        Log In Now
                    </Link>
                </p>
            </form>
        </AuthLayout>
    )
}

export default SignUpPage
