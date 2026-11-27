import apiClient, { getShared } from "./client";

export async function getCurrentCompany() {
  const response = await getShared(`/companies/me`);
  return response.data;
}

export async function getCompany(id) {
  const response = await getShared(`/companies/${id}`);
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
