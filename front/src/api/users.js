import apiClient from "./client";

export async function getCurrentUser() {
  const response = await apiClient.get(`/users/me`);
  return response.data;
}

export async function updateUser(data) {
  const response = await apiClient.put(`/users/me`, data);
  return response.data;
}

export async function deleteUser() {
  const response = await apiClient.delete(`/users/me`);
  return response.data;
}

export async function createUser(data) {
  const response = await apiClient.post(`/users`, data);
  return response.data;
}
