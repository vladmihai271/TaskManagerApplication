import React, { useState } from "react";
import "./ProjectPage.scss";
import { getUserData } from "../../../const/utils";
import { checkRights, getUserRights } from "../../../const/rightsUtils";
import updateProject from "../../../api/updateProject";
import deleteProject from "../../../api/deleteProject";
// import SprintSvg from "../../assets/SprintSvg";

export default function ProjectPage(props) {
  const { uid, title, status, hidden, team, description } = props.project;

  const [changeStatus, setChangeStatus] = useState(false);
  const [newStatus, setNewStatus] = useState("");

  console.log(props.project);

  const handleUpdateProject = async (field) => {
    let changes = {};
    switch (field) {
      case "status":
        changes["status"] = newStatus;
        break;
      default:
        console.log("Unknown field for update: ", field);
        return;
    }

    await updateProject(uid, getUserData().userId, changes);
    // alert("Sprint updated!");
    window.location.reload();
  };

  const handleDeleteProject = async (uid) => {
    const deleteResponse = await deleteProject(uid, getUserData().userId);
    if (deleteResponse.ok) {
      alert("Project deleted!");
      window.location.reload();
    } else {
      alert("Project could not be deleted!");
    }
  };

  return (
    <div className="ProjectPage">
      {/* <SprintSvg /> */}
      <h2>{title}</h2>

      {checkRights("delete-project") && (
        <button
          onClick={() => {
            handleDeleteProject(uid);
          }}
        >
          Delete Project
        </button>
      )}

      <div id="project-info">
        <div id="project-main-info">
          <p>
            Status:
            {!changeStatus ? (
              <span> {status}</span>
            ) : (
              <select
                onClick={(e) => {
                  setNewStatus(e.target.value);
                }}
                defaultValue={status}
              >
                <option value={"Active"}>Active</option>
                <option value={"In progress"}>In progress</option>
                <option value={"Closed"}>Closed</option>
              </select>
            )}
          </p>
          <p>
            Team:
            <span> {team}</span>
          </p>

          <p>
            Hidden:
            <span> {hidden ? "Hidden" : "Visible for all"}</span>
          </p>
        </div>

        {getUserRights("update-project") && (
          <button
            onClick={() => {
              changeStatus ? handleUpdateProject("status") : setChangeStatus(!changeStatus);
            }}
          >
            {changeStatus ? "Save" : "Change status"}
          </button>
        )}

        <div id="project-desc">
          <span>Description</span>
          <div>{description}</div>
        </div>
      </div>
    </div>
  );
}
