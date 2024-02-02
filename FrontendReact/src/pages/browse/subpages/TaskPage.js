import React, { useState } from "react";
import "./TaskPage.scss";
import TaskSvg from "../../../assets/task";
import updateTask from "../../../api/updateTask";
import { getUserData } from "../../../const/utils";

export default function TaskPage(props) {
  const { title, description, sprint, status, project, assignedTo, storyPoints, assignee, uid } = props.task;

  console.log(props.task);

  const [changeStatus, setChangeStatus] = useState(false);
  const [newStatus, setNewStatus] = useState("");

  const handleUpdateTask = async (field) => {
    let changes = {};
    switch (field) {
      case "status":
        changes["status"] = newStatus;
        break;
      case "assignee":
        changes["assignee"] = "new";
      default:
        console.log("Unknown field for update: ", field);
        return;
    }

    console.log(changes);

    const updateResponse = await updateTask(uid, getUserData().userId, changes);

    if (updateResponse) {
      alert("Task updated!");
      window.location.reload();
    } else {
      alert("Task could not be updated!");
    }
  };

  return (
    <div className="TaskPage">
      <TaskSvg />
      <h2>{title}</h2>
      <button>Delete task</button>
      <div id="task-info">
        <div id="task-main-info">
          <p>
            Status:
            {changeStatus ? (
              <>
                <select
                  onClick={(e) => {
                    setNewStatus(e.target.value);
                  }}
                  defaultValue={status}
                >
                  <option value="Active">Active</option>
                  <option value="In progress">In progress</option>
                  <option value="Closed">Closed</option>
                </select>
                <button
                  onClick={() => {
                    handleUpdateTask("status");
                  }}
                >
                  Save
                </button>
              </>
            ) : (
              <>
                <span> {status}</span>
                <button
                  onClick={() => {
                    setChangeStatus(!changeStatus);
                  }}
                >
                  Change
                </button>
              </>
            )}
          </p>
          <p>
            Assigned to:
            <span> {assignee}</span>
          </p>
        </div>

        <div id="task-desc">
          <span>Description</span>
          <p>{description}</p>
        </div>

        <div id="task-side-info">
          <p>
            Sprint:
            <span> {sprint}</span>
          </p>

          <p>
            Project:
            <span> {project}</span>
          </p>

          <p>
            Story points:
            <span> {storyPoints}</span>
          </p>
        </div>
      </div>
    </div>
  );
}
