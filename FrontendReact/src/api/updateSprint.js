import request from "./request";

// Function to make a request
const updateSprint = async (taskId, userId, body) => {
  const endpoint = `sprints/${taskId}/${userId}`;

  const response = await request(endpoint, {}, body, "PUT");

  return response;
};

export default updateSprint;
