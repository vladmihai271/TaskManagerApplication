import request from "./request";

// Function to make a request
const updateProject = async (taskId, userId, body) => {
  const endpoint = `projects/${taskId}/${userId}`;

  const response = await request(endpoint, {}, body, "PUT");

  return response;
};

export default updateProject;
