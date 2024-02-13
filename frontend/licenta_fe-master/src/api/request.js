// Function to make a request
const request = async (endpoint, parameters = {}, body = null, method = "GET") => {
  const apiUrl = `http://localhost:8080/${endpoint}?${new URLSearchParams(parameters).toString()}`;

  const options = {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
    body: body ? JSON.stringify(body) : undefined,
  };

  const response = await fetch(apiUrl, options);

  if (!response.ok) {
    throw new Error(`Request failed with status ${response.status}`);
  }

  try {
    const data = await response.json();
    return data;
  } catch (err) {
    console.log(err);
    console.log(response);
    return response;
  }
};

export default request;
