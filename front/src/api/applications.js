import apiClient from "./client";

export async function getCurrentApplication() {
  const response = await apiClient.get(`/applications/me`);
  return response.data;
}

export async function getApplication(id) {
  const response = await apiClient.get(`/applications/${id}`);
  return response.data;
}

export async function createApplication(data) {
  const response = await apiClient.post(`/applications/me`, data);
  return response.data;
}

export async function updateApplication(id, data) {
  const response = await apiClient.put(`/applications/${id}`, data);
  return response.data;
}

export async function deleteApplication(id) {
  const response = await apiClient.delete(`/applications/${id}`);
  return response.data;
}
