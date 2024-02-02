import request from "./request";

// Function to make a request
const deleteProject = async (projectsId, userId) => {
  const endpoint = `projects/${projectsId}/${userId}`;

  const response = await request(endpoint, {}, null, "DELETE");

  console.log(response);
  return response;
};

export default deleteProject;
