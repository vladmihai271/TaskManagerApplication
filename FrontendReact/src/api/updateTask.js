import request from "./request";

// Function to make a request
const updateTask = async (taskId, userId, body) => {
  const endpoint = `tasks/${taskId}/${userId}`;

  const response = await request(endpoint, {}, body, "PUT");

  return response;
};

export default updateTask;
