import request from "./request";

// Function to make a request
const login = async (username, password) => {
  const endpoint = "login";
  const params = {
    username: username,
    password: password,
  };

  const response = await request(endpoint, params, null, "POST");

  return response;
};

export default login;
