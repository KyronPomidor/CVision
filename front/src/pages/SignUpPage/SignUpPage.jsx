import { useState } from "react"
import { Link } from "react-router-dom"
import AuthLayout from "../../components/AuthLayout/AuthLayout"
import AuthInput from "../../components/AuthLayout/AuthInput"
import userIcon from "../../assets/user-icon.svg"
import mailIcon from "../../assets/mail-icon.svg"
import lockIcon from "../../assets/lock-icon.svg"
import bagIcon from "../../assets/bag-logo.svg"
import styles from "./SignUpPage.module.css"

const ROLE_OPTIONS = [
    { value: "", label: "Select Role", disabled: true, hidden: true },
    { value: "EMPLOYEE", label: "Employee" },
    { value: "EMPLOYER", label: "Employer" },
]

function SignUpPage() {
    const [form, setForm] = useState({
        fullName: "",
        email: "",
        role: "",
        password: "",
        confirmPassword: "",
    })

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
                    icon={bagIcon}
                    options={ROLE_OPTIONS}
                    value={form.role}
                    onChange={updateField("role")}
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