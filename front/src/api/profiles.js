import apiClient from "./client";

export async function getCurrentProfile(signal) {
  const response = await apiClient.get("/profiles/me", { signal });
  return response.data;
}

export async function updateAboutMe(description) {
  const response = await apiClient.put("/profiles/me/about-me", { description });
  return response.data;
}

export async function getProfile(id) {
  const response = await apiClient.get(`/profiles/${id}`);
  return response.data;
}

export async function getProfileByUser(userId) {
  const response = await apiClient.get(`/profiles/user/${userId}`);
  return response.data;
}

export async function createProfile(userId, data) {
  const response = await apiClient.post(`/profiles/user/${userId}`, data);
  return response.data;
}

export async function updateProfile(id, data) {
  const response = await apiClient.put(`/profiles/${id}`, data);
  return response.data;
}

export async function deleteProfile(id) {
  await apiClient.delete(`/profiles/${id}`);
}
