import apiClient from "./client";

export async function getCurrentCV() {
  const response = await apiClient.get(`/cv/me`);
  return response.data;
}

export async function getCV(id) {
  const response = await apiClient.get(`/cv/${id}`);
  return response.data;
}

export async function createCV(data) {
  const response = await apiClient.post(`/cv/me`, data);
  return response.data;
}

export async function uploadCV(file) {
  const data = new FormData();
  data.append("file", file);
  const response = await apiClient.post(`/cv/me`, data);
  return response.data;
}

export async function updateCV(id, data) {
  const response = await apiClient.put(`/cv/${id}`, data);
  return response.data;
}

export async function deleteCV(id) {
  const response = await apiClient.delete(`/cv/${id}`);
  return response.data;
}

export async function updateCVInformation(data) {
  const response = await apiClient.put("/cv/me/information", data);
  return response.data;
}
