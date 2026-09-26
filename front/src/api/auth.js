import apiClient from "./client";
import { createUser } from "./users";

export async function login(email, password) {
  const response = await apiClient.post("/auth/login", { email, password });
  const { token } = response.data;
  localStorage.setItem("token", token);
  return response.data;
}

export async function signup({ accountName, email, password, role }){
  await createUser({ accountName, email, password, role});
  return login(email, password);
}

export function logout() {
  localStorage.removeItem("token");
}
