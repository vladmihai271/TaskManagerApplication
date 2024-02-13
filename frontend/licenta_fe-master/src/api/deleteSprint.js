import request from "./request";

// Function to make a request
const deleteSprint = async (sprintId, userId) => {
  const endpoint = `sprints/${sprintId}/${userId}`;

  const response = await request(endpoint, {}, null, "DELETE");

  console.log(response);
  return response;
};

export default deleteSprint;
