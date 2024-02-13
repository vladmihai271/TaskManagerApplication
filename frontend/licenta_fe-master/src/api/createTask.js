import request from "./request";

// Function to make a request
const createTask = async (userId, body) => {
  const endpoint = `tasks/${userId}`;

  const response = await request(
    endpoint,
    {},
    {
      ...body,
      uid: Date.now(),
    },
    "POST",
  );

  return response;
};

export default createTask;
