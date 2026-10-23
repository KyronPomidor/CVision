import { useEffect, useState } from "react";
import { Navigate, useLocation, useNavigate } from "react-router-dom";
import { verifyTwoFactor, resendCode } from "../../api/auth";
import LockLogo from "../../assets/lock-icon.svg?react";
import styles from "../../components/AuthLayout/AuthCard.module.css";
import AuthInput from "../../components/AuthLayout/AuthInput";
import AuthLayout from "../../components/AuthLayout/AuthLayout";
import typography from "../../Typography.module.css";

const RESEND_COOLDOWN = 30; //seconds

function TwoFactorPage() {
  const [error, setError] = useState("");
  const [info, setInfo] = useState("");
  const [code, setCode] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [cooldown, setCooldown] = useState(RESEND_COOLDOWN);
  const navigate = useNavigate();
  const location = useLocation();

  const preAuthToken = location.state?.preAuthToken;
  const redirectTo = location.state?.from || "/";

  useEffect(() => {
    if (cooldown <= 0) return;
    const id = setTimeout(() => setCooldown((c) => c - 1), 1000);
    return () => clearTimeout(id);
  }, [cooldown]);

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

  async function handleResend() {
    setError("");
    setInfo("");
    setCooldown(RESEND_COOLDOWN);
    try {
      await resendCode(preAuthToken);
      setInfo("A new code has been sent.");
    } catch (err) {
      setCooldown(0);
      const status = err.response?.status;
      if (status === 429) {
        setError("Please wait before requesting another code.");
      } else if (status === 401) {
        setError("Session expired. Please log in again.");
      } else {
        setError("Could not resend the code. Please try again.");
      }
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
        <button type="button" disabled={cooldown > 0} className={`${typography.mainTextImportant} ${styles.submitButton} ${styles.resendButton}`} onClick={handleResend}>
          {cooldown > 0 ? `Resend code (${cooldown}s)` : "Resend code"}
        </button>
      </form>
      {(error || info) && (
        <div className={`${typography.mainTextImportant} ${styles.errorWindow} ${styles.card}`}>
          {error || info}
        </div>
      )}
    </AuthLayout >
  )
}

export default TwoFactorPage;
