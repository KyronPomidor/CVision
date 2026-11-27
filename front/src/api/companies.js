import apiClient from "./client";

export async function getCurrentCompany() {
  const response = await apiClient.get(`/companies/me`);
  return response.data;
}

export async function getCompany(id) {
  const response = await apiClient.get(`/companies/${id}`);
  return response.data;
}

export async function createCompany(data) {
  const response = await apiClient.post(`/companies/me`, data);
  return response.data;
}

export async function updateCompany(data) {
  const response = await apiClient.put(`/companies/me`, data);
  return response.data;
}

export async function deleteCompany() {
  const response = await apiClient.delete(`/companies/me`);
  return response.data;
}

export async function uploadCompanyPicture(file) {
  const data = new FormData();
  data.append("file", file);
  const response = await apiClient.post(`/companies/me/picture`, data);
  return response.data;
}

export async function deleteCompanyPicture() {
  const response = await apiClient.delete(`/companies/me/picture`);
  return response.data;
}
