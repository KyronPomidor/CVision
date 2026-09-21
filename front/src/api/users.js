import apiClient from "./client";

export async function getUser(id) {
    const response = await apiClient.get(`/users/${id}`);
    return response.data;
}

export async function updateUser(id, data) {
    const response = await apiClient.put(`/users/${id}`, data);
    return response.data;
}

export async function deleteUser(id) {
    await apiClient.delete(`/users/${id}`);
}

export async function createUser(data) {
    const response = await apiClient.post(`/users`, data);
    return response.data;
}
