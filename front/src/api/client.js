// src/api/client.js
import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
  withCredentials: true,
});

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const isAuthRoute = error.config?.url?.startsWith("/auth");
    if (error.response?.status === 401 && !isAuthRoute) {
      logout();
    }
    return Promise.reject(error);
  },
);

export function logout() {
  apiClient.post("/auth/logout").catch(() => {});
  localStorage.removeItem("isLoggedIn");
  if (window.location.pathname !== "/login") {
    window.location.href = "/login";
  }
}

export default apiClient;
