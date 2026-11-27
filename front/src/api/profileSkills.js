import apiClient from "./client";

export async function getProfileSkills() {
  const response = await apiClient.get(`/profiles/me/skills`);
  return response.data;
}

export async function getProfileSkill(id) {
  const response = await apiClient.get(`/profiles/me/skills/${id}`);
  return response.data;
}

export async function addProfileSkill(data) {
  const response = await apiClient.post(`/profiles/me/skills`, data);
  return response.data;
}

export async function updateProfileSkill(id, data) {
  const response = await apiClient.put(`/profiles/me/skills/${id}`, data);
  return response.data;
}

export async function deleteProfileSkill(id) {
  const response = await apiClient.delete(`/profiles/me/skills/${id}`);
  return response.data;
}
