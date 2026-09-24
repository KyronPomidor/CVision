import apiClient from "./client";

export async function getCompany(id) {
  const response = await apiClient.get(`/companies/${id}`);
  return response.data;
}

export async function getCompanyByUser(userId) {
  const response = await apiClient.get(`/companies/user/${userId}`);
  return response.data;
}

export async function createCompany(userId, data) {
  const response = await apiClient.post(`/companies/user/${userId}`, data);
  return response.data;
}

export async function updateCompany(id, data) {
  const response = await apiClient.put(`/companies/${id}`, data);
  return response.data;
}

export async function deleteCompany(id) {
  await apiClient.delete(`/companies/${id}`);
}
