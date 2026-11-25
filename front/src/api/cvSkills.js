import apiClient from "./client";

export async function getCurrentCVSkills() {
  const response = await apiClient.get(`/cv/me/skills`);
  return response.data;
}

export async function getCVSkill(id) {
  const response = await apiClient.get(`/cv/me/skills/${id}`);
  return response.data;
}

export async function createCVSkill(data) {
  const response = await apiClient.post(`/cv/me/skills`, data);
  return response.data;
}

export async function updateCVSkill(id, data) {
  const response = await apiClient.put(`/cv/me/skills/${id}`, data);
  return response.data;
}

export async function deleteCVSkill(id) {
  const response = await apiClient.delete(`/cv/me/skills/${id}`);
  return response.data;
}
