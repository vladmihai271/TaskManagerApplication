import "./App.scss";
import { Route, useNavigate } from "react-router";
import { Routes } from "react-router-dom";
import LoginPage from "./pages/credentials/LoginPage";
import BrowsePage from "./pages/browse/BrowsePage";
import { useEffect } from "react";
import request from "./api/request";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/login" element={<LoginPage />}></Route>
        <Route path="/" element={<BrowsePage />}></Route>
      </Routes>
    </div>
  );
}

export default App;
