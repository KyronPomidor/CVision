import apiClient, { getShared } from "./client";

export async function getJobPosting(id) {
  const response = await getShared(`/postings/${id}`);
  return response.data;
}

export async function getCurrentCompanyPostings() {
  const response = await getShared(`/companies/me/postings`);
  return response.data;
}

export async function getMatchedPostings(query = "") {
  const response = await getShared(`/postings/matches/me?${query}`);
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
  const response = await getShared(`/postings/${id}/applicants`);
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

export async function getCompanyMatches(companyId) {
  const items = [];
  let page = 0;
  let result;
  do {
    result = await getMatchedPostings(new URLSearchParams({ companyId, page }));
    items.push(...result.items);
    page++;
  } while (page < result.totalPages);
  return items;
}

export async function updateRequiredYears(postingId, skillId, years) {
  const response = await apiClient.put(`/postings/${postingId}/skills/${skillId}/experience`, { years });
  return response.data;
}
