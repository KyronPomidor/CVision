import apiClient from "./client";

export async function getJobPosting(id) {
  const response = await apiClient.get(`/postings/${id}`);
  return response.data;
}

export async function getJobPostingsByCompany(companyId) {
  const response = await apiClient.get(`/postings/company/${companyId}`);
  return response.data;
}

export async function createJobPosting(companyId, data) {
  const response = await apiClient.post(`/postings/company/${companyId}`, data);
  return response.data;
}

export async function updateJobPosting(id, data) {
  const response = await apiClient.put(`/postings/${id}`, data);
  return response.data;
}

export async function deleteJobPosting(id) {
  await apiClient.delete(`/postings/${id}`);
}
