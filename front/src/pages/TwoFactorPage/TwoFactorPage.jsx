import { useState } from "react";
import { Navigate, useLocation, useNavigate } from "react-router-dom";
import { verifyTwoFactor } from "../../api/auth";
import LockLogo from "../../assets/lock-icon.svg?react";
import styles from "../../components/AuthLayout/AuthCard.module.css";
import AuthInput from "../../components/AuthLayout/AuthInput";
import AuthLayout from "../../components/AuthLayout/AuthLayout";
import typography from "../../Typography.module.css";

function handleResend() {

}

function TwoFactorPage() {
  const [code, setCode] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const preAuthToken = location.state?.preAuthToken;
  const redirectTo = location.state?.from || "/";

  if (!preAuthToken) {
    return <Navigate to="/login" replace />;
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setSubmitting(true);

    try {
      await verifyTwoFactor(preAuthToken, code.trim());
      navigate(redirectTo, { replace: true });
    } catch (err) {
      const status = err.response?.status;
      if (status === 400 || status === 401) {
        setError("Invalid or expired code. Please try again.");
      } else if (status === 429) {
        setError("Too many attempts. Please wait and try again.");
      } else {
        setError("Something went wrong. Please try again.");
      }
      setSubmitting(false);
    }
  }

  return (
    <AuthLayout>
      <form className={`${styles.mainWindow} ${styles.card}`} onSubmit={handleSubmit}>
        <h2 className={`${typography.heading2} ${styles.hintText}`}>Check your inbox</h2>
        <p className={`${typography.mainTextImportant} ${styles.hintText}`}>Enter the verification code we just sent to your email.</p>
        <AuthInput
          icon={LockLogo}
          type="text"
          placeholder="Code"
          value={code}
          onChange={(e) => setCode(e.target.value)}
          required
        />
        <button type="submit" disabled={submitting} className={`${typography.mainTextImportant} ${styles.submitButton}`}>
          Verify
        </button>
        <button type="button" className={`${typography.mainTextImportant} ${styles.submitButton} ${styles.resendButton}`} onClick={handleResend}>
          Resend code
        </button>
      </form>
      {error && (
        <div className={`${typography.mainTextImportant} ${styles.errorWindow} ${styles.card}`}>
          {error}
        </div>
      )}
    </AuthLayout >
  )
}

export default TwoFactorPage;
