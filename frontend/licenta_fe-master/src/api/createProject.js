import request from "./request";

// Function to make a request
const createProject = async (userId, body) => {
  const endpoint = `projects/${userId}`;

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

export default createProject;
