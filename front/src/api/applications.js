import apiClient from "./client";

export async function getApplication(id) {
  const response = await apiClient.get(`/applications/${id}`);
  return response.data;
}

export async function getApplicationsByUser(userId) {
  const response = await apiClient.get(`/applications/user/${userId}`);
  return response.data;
}

export async function createApplication(userId, data) {
  const response = await apiClient.post(`/applications/user/${userId}`, data);
  return response.data;
}

export async function updateApplication(id, data) {
  const response = await apiClient.put(`/applications/${id}`, data);
  return response.data;
}

export async function deleteApplication(id) {
  await apiClient.delete(`/applications/${id}`);
}
