import apiClient from "./client";

export async function getCurrentProfile() {
  const response = await apiClient.get(`/profiles/me`);
  return response.data;
}

export async function getProfile(id) {
  const response = await apiClient.get(`/profiles/${id}`);
  return response.data;
}

export async function createProfile(data) {
  const response = await apiClient.post(`/profiles/me`, data);
  return response.data;
}

export async function updateProfile(data) {
  const response = await apiClient.put(`/profiles/me`, data);
  return response.data;
}

export async function deleteProfile() {
  const response = await apiClient.delete(`/profiles/me`);
  return response.data;
}

export async function uploadProfilePicture(file) {
  const data = new FormData();
  data.append("file", file);
  const response = await apiClient.post(`/profiles/me/picture`, data);
  return response.data;
}

export async function deleteProfilePicture() {
  const response = await apiClient.delete(`/profiles/me/picture`);
  return response.data;
}
