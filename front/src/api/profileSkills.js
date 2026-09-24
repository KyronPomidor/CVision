import apiClient from "./client";

export async function getProfileSkills(profileId) {
  const response = await apiClient.get(`/profiles/${profileId}/skills`);
  return response.data;
}

export async function addProfileSkill(profileId, data) {
  const response = await apiClient.post(`/profiles/${profileId}/skills`, data);
  return response.data;
}

export async function getProfileSkill(id) {
  const response = await apiClient.get(`/profiles/skills/${id}`);
  return response.data;
}

export async function updateProfileSkill(id, data) {
  const response = await apiClient.put(`/profiles/skills/${id}`, data);
  return response.data;
}

export async function deleteProfileSkill(id) {
  await apiClient.delete(`/profiles/skills/${id}`);
}
