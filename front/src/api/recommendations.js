import apiClient from "./client";

export async function getRecommendation(id) {
    const response = await apiClient.get(`/recommendations/${id}`);
    return response.data;
}

export async function getRecommendationsByUser(userId) {
    const response = await apiClient.get(`/recommendations/user/${userId}`);
    return response.data;
}

export async function getRecommendationsByJobPosting(jobPostingId) {
    const response = await apiClient.get(`/recommendations/job/${jobPostingId}`);
    return response.data;
}

export async function createRecommendation(userId, data) {
    const response = await apiClient.post(`/recommendations/user/${userId}`, data);
    return response.data;
}

export async function updateRecommendation(id, data) {
    const response = await apiClient.put(`/recommendations/${id}`, data);
    return response.data;
}

export async function deleteRecommendation(id) {
    await apiClient.delete(`/recommendations/${id}`);
}
