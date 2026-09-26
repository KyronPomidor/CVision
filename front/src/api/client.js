// src/api/client.js
import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api",
});

apiClient.interceptors.request.use((config) => {
  const isAuthRoute = config.url?.startsWith("/auth") || config.url === "/users";
  const token = localStorage.getItem("token");
  if (token && !isAuthRoute) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if(error.response?.status === 401) {
      logout();
    }
    return Promise.reject(error);
  }
);

function logout() {
  localStorage.removeItem("token");
  if (window.location.pathname !== "/login") {
    window.location.href = "/login";
  }
}

export default apiClient;
