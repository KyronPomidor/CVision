import apiClient from "./client";

export async function askQuestion(question) {
  const response = await apiClient.post("/perplexity/ask", { question });
  return response.data;
}
