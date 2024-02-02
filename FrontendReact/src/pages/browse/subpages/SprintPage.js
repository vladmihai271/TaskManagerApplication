import React, { useState } from "react";
import "./SprintPage.scss";
import SprintSvg from "../../../assets/SprintSvg";
import updateSprint from "../../../api/updateSprint";
import { getUserData } from "../../../const/utils";
import { checkRights, getUserRights } from "../../../const/rightsUtils";
import deleteSprint from "../../../api/deleteSprint";

export default function SprintPage(props) {
  const { uid, title, status, project, team, tasks } = props.sprint;

  const [changeStatus, setChangeStatus] = useState(false);
  const [newStatus, setNewStatus] = useState("");

  const handleUpdateSprint = async (field) => {
    let changes = {};
    switch (field) {
      case "status":
        changes["status"] = newStatus;
        break;
      default:
        console.log("Unknown field for update: ", field);
        return;
    }

    await updateSprint(uid, getUserData().userId, changes);
    alert("Sprint updated!");
    window.location.reload();
  };

  const handleDeleteSprint = async (uid) => {
    const deleteResponse = await deleteSprint(uid, getUserData().userId);

    if (deleteResponse.ok) {
      alert("Sprint deleted!");
      window.location.reload();
    } else {
      alert("Sprint could not be deleted!");
    }
  };

  return (
    <div className="SprintPage">
      <SprintSvg />
      <h2>{title}</h2>

      {checkRights("delete-sprint") && (
        <button
          onClick={() => {
            handleDeleteSprint(uid);
          }}
        >
          Delete sprint
        </button>
      )}

      <div id="sprint-info">
        <div id="sprint-main-info">
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
            Project:
            <span> {project}</span>
          </p>
          <p>
            Team:
            <span> {team}</span>
          </p>
        </div>

        {getUserRights("update-sprint") && (
          <button
            onClick={() => {
              changeStatus ? handleUpdateSprint("status") : setChangeStatus(!changeStatus);
            }}
          >
            {changeStatus ? "Save" : "Change status"}
          </button>
        )}

        <div id="sprint-desc">
          <span>Tasks</span>
          <div>
            {tasks.split(",").map((t, idx) => (
              <p id={`task-${idx}`}>&bull; {t}</p>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
