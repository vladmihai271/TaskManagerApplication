import request from "./request";

// Function to make a request
const deleteEmployee = async (employeeId, userId) => {
  const endpoint = `employees/${employeeId}/${userId}`;

  const response = await request(endpoint, {}, null, "DELETE");

  console.log(response);
  return response;
};

export default deleteEmployee;
