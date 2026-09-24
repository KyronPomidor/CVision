import { useState } from "react";
import { Link } from "react-router-dom";
import AuthLayout from "../../components/AuthLayout/AuthLayout";
import AuthInput from "../../components/AuthLayout/AuthInput";
import UserLogo from "../../assets/user-icon.svg?react";
import MailLogo from "../../assets/mail-icon.svg?react";
import LockLogo from "../../assets/lock-icon.svg?react";
import BriefcaseLogo from "../../assets/briefcase-logo.svg?react";
import typography from "../../Typography.module.css";
import styles from "../../components/AuthLayout/AuthCard.module.css";

const ROLE_OPTIONS = [
  { value: "", label: "Select Role", disabled: true, hidden: true },
  { value: "EMPLOYEE", label: "Employee" },
  { value: "EMPLOYER", label: "Employer" },
];

function SignUpPage() {
  const [form, setForm] = useState({
    fullName: "",
    email: "",
    role: "",
    password: "",
    confirmPassword: "",
  });

  function updateField(field) {
    return (e) => setForm((prev) => ({ ...prev, [field]: e.target.value }));
  }

  function handleSubmit(e) {
    e.preventDefault();
    // TODO: call sign up API
  }

  return (
    <AuthLayout mapOffset={-390}>
      <form className={styles.card} onSubmit={handleSubmit}>
        <h1 className={`${typography.heading1} ${styles.title}`}>Create CVision Account</h1>

        <AuthInput
          icon={UserLogo}
          type="text"
          placeholder="Full Name"
          value={form.fullName}
          onChange={updateField("fullName")}
          required
        />

        <AuthInput
          icon={MailLogo}
          type="email"
          placeholder="Email Address"
          value={form.email}
          onChange={updateField("email")}
          required
        />

        <AuthInput
          icon={BriefcaseLogo}
          options={ROLE_OPTIONS}
          value={form.role}
          onChange={updateField("role")}
          required
        />

        <AuthInput
          icon={LockLogo}
          type="password"
          placeholder="Password"
          value={form.password}
          onChange={updateField("password")}
          required
        />

        <AuthInput
          icon={LockLogo}
          type="password"
          placeholder="Confirm Password"
          value={form.confirmPassword}
          onChange={updateField("confirmPassword")}
          required
        />

        <button type="submit" className={`${typography.mainTextImportant} ${styles.submitButton}`}>
          Sign Up
        </button>

        <p className={`${typography.mainTextImportant} ${styles.switchText}`}>
          Already have an account?{" "}
          <Link to="/login" className={styles.switchLink}>
            Log In Now
          </Link>
        </p>
      </form>
    </AuthLayout>
  );
}

export default SignUpPage;
