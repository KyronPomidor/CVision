import apiClient, { logout } from "./client";
import { createUser } from "./users";

export async function login(email, password) {
  const response = await apiClient.post("/auth/login", { email, password });
  const data = response.data;

  if (!data?.twoFactorRequired) {
    localStorage.setItem("isLoggedIn", "true");
  }
  return data;
}

export async function verifyTwoFactor(preAuthToken, code) {
  const response = await apiClient.post("/auth/2fa/verify", { preAuthToken, code });
  localStorage.setItem("isLoggedIn", "true");
  return response.data;
}

export async function enableTwoFactor() {
  const response = await apiClient.post("/auth/2fa/enable");
  return response.data;
}

export async function disableTwoFactor() {
  const response = await apiClient.post("/auth/2fa/disable");
  return response.data;
}

export async function resendCode(preAuthToken) {
  const response = await apiClient.post("/auth/2fa/resend", { preAuthToken });
  return response.data;
}

export async function signup({ accountName, email, password, role }) {
  await createUser({ accountName, email, password, role });
  return login(email, password);
}

export { logout };
