import request from "./request";

// Function to make a request
const createSprint = async (userId, body) => {
  const endpoint = `sprints/${userId}`;

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

export default createSprint;
