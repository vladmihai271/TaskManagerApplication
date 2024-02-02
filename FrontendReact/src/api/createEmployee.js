import { hashPassword } from "../const/utils";
import request from "./request";

// Function to make a request
const createEmployee = async (userId, body) => {
  const endpoint = `employees/${userId}`;

  const response = await request(
    endpoint,
    {},
    {
      ...body,
      password: hashPassword(body.password),
    },
    "POST",
  );

  return response;
};

export default createEmployee;
