import apiClient from "./client";

export async function getSkill(id) {
    const response = await apiClient.get(`/skills/${id}`);
    return response.data;
}

export async function getAllSkills() {
    const response = await apiClient.get(`/skills`);
    return response.data;
}

export async function updateSkill(id, data) {
    const response = await apiClient.put(`/skills/${id}`, data);
    return response.data;
}

export async function deleteSkill(id) {
    await apiClient.delete(`/skills/${id}`);
}

export async function createSkill(data) {
    const response = await apiClient.post(`/skills`, data);
    return response.data;
}
