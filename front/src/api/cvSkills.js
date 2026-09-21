import apiClient from "./client";

export async function getCVSkillsByCv(cvId) {
    const response = await apiClient.get(`/cv-skills/cv/${cvId}`);
    return response.data;
}

export async function getCVSkillsBySkill(skillId) {
    const response = await apiClient.get(`/cv-skills/skill/${skillId}`);
    return response.data;
}

export async function createCVSkill(cvId, data) {
    const response = await apiClient.post(`/cv-skills/cv/${cvId}`, data);
    return response.data;
}

export async function updateCVSkill(id, data) {
    const response = await apiClient.put(`/cv-skills/${id}`, data);
    return response.data;
}

export async function deleteCVSkill(id) {
    await apiClient.delete(`/cv-skills/${id}`);
}
