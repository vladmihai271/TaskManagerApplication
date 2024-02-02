import { useState } from "react";
import "./credentials.scss";
import login from "../../api/login";
import { hashPassword, setCredentialsToLocalStorage } from "../../const/utils";
import { useNavigate } from "react-router-dom";

function LoginPage() {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [errorMessage, setErrorMessage] = useState();

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const encryptedPassword = hashPassword(password);

    const loginResponseData = await login(username, encryptedPassword);

    if (!loginResponseData.Authenticated) {
      setErrorMessage(loginResponseData["Authentication message"]);
    } else {
      setCredentialsToLocalStorage(
        username,
        encryptedPassword,
        loginResponseData.userId,
        loginResponseData.AccessRights,
      );
      navigate("/");
    }
  };
  return (
    <div id="credentials-page">
      <form onSubmit={handleSubmit}>
        <h2>Login</h2>
        <label>Username</label>
        <input
          onChange={(e) => {
            setUsername(e.target.value);
          }}
        />

        <label>Password</label>
        <input
          type="password"
          onChange={(e) => {
            setPassword(e.target.value);
          }}
        />

        <span>{errorMessage}</span>
        <button>Submit</button>
      </form>
    </div>
  );
}

export default LoginPage;
