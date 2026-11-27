// src/api/client.js
import axios from "axios";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api",
  withCredentials: true,
});

const pendingGets = new Map();

apiClient.interceptors.request.use((config) => {
  if (config.url?.startsWith("/auth/")) pendingGets.clear();
  return config;
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

export function getShared(url) {
  const pending = pendingGets.get(url);
  if (pending) return pending;

  const request = apiClient.get(url).finally(() => {
    if (pendingGets.get(url) === request) pendingGets.delete(url);
  });
  pendingGets.set(url, request);
  return request;
}

// A missing optional resource is expected for newly registered accounts.
export async function optionalResource(request) {
  try {
    return await request;
  } catch (error) {
    if (error.response?.status === 404) return null;
    throw error;
  }
}

export function mediaUrl(path) {
  if (!path) return undefined;
  if (/^https?:\/\//i.test(path)) return path;
  const base = new URL(apiClient.defaults.baseURL, window.location.origin);
  return new URL(path, base.origin).href;
}

export function errorMessage(error) {
  if (error.response?.status === 403) return "You do not have permission to view this resource.";
  if (error.response?.status === 404) return "This resource was not found.";
  if (error.response?.status === 429) return "Too many requests. Please try again shortly.";
  const message = error.response?.data?.error || error.response?.data;
  return typeof message === "string" && message.length < 300
    ? message
    : "Could not complete the request. Please try again.";
}
