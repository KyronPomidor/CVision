import apiClient from "./client";

export async function getJobPosting(id) {
  const response = await apiClient.get(`/postings/${id}`);
  return response.data;
}

export async function getCurrentCompanyPostings() {
  const response = await apiClient.get(`/companies/me/postings`);
  return response.data;
}

export async function getMatchedPostings() {
  const response = await apiClient.get(`/postings/matches/me`);
  return response.data;
}

export async function createJobPosting(data) {
  const response = await apiClient.post(`/companies/me/postings`, data);
  return response.data;
}

export async function updateJobPosting(id, data) {
  const response = await apiClient.put(`/postings/${id}`, data);
  return response.data;
}

export async function deleteJobPosting(id) {
  const response = await apiClient.delete(`/postings/${id}`);
  return response.data;
}

export async function getPostingApplicants(id) {
  const response = await apiClient.get(`/postings/${id}/applicants`);
  return response.data;
}

export async function uploadPostingImages(id, files) {
  const data = new FormData();
  files.forEach((file) => data.append("files", file));
  const response = await apiClient.post(`/postings/${id}/images`, data);
  return response.data;
}

export async function deletePostingImage(id, imageId) {
  await apiClient.delete(`/postings/${id}/images/${imageId}`);
}
