import { SHA256 } from "crypto-js";

export const setCredentialsToLocalStorage = (user, pass, id, rights) => {
  localStorage.setItem("username", user);
  localStorage.setItem("password", pass);
  localStorage.setItem("userId", id);
  localStorage.setItem("rights", rights);
};

export const getUserData = () => {
  return {
    username: localStorage.getItem("username"),
    password: localStorage.getItem("password"),
    userId: localStorage.getItem("userId"),
    rights: localStorage.getItem("rights"),
  };
};

export function hashPassword(password) {
  // Hash the password using SHA-256
  const hashedPassword = SHA256(password).toString();
  return hashedPassword;
}
