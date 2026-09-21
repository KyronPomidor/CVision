import apiClient from "./client";

export async function getCV(id) {
    const response = await apiClient.get(`/cv/${id}`);
    return response.data;
}

export async function getCVsByUser(userId) {
    const response = await apiClient.get(`/cv/user/${userId}`);
    return response.data;
}

export async function createCV(userId, data) {
    const response = await apiClient.post(`/cv/user/${userId}`, data);
    return response.data;
}

export async function updateCV(id, data) {
    const response = await apiClient.put(`/cv/${id}`, data);
    return response.data;
}

export async function deleteCV(id) {
    await apiClient.delete(`/cv/${id}`);
}
