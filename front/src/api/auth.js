import apiClient, { logout } from "./client";
import { createUser } from "./users";

export async function login(email, password) {
  const response = await apiClient.post("/auth/login", { email, password });
  localStorage.setItem("isLoggedIn", "true");
  return response.data;
}

export async function signup({ accountName, email, password, role }) {
  await createUser({ accountName, email, password, role });
  return login(email, password);
}

export { logout };
