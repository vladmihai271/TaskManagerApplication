import { hashPassword } from "../const/utils";
import request from "./request";

// Function to make a request
const updateEmployee = async (employeeId, userId, body) => {
  const endpoint = `employees/${employeeId}/${userId}`;

  const response = await request(endpoint, {}, body, "PUT");

  return response;
};

export default updateEmployee;
