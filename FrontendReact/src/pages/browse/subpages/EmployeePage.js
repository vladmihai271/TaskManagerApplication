import { useState } from "react";
import deleteEmployee from "../../../api/deleteEmployee";
import AvatarSvg from "../../../assets/avatar";
import { getUserData, hashPassword } from "../../../const/utils";
import "./EmployeePage.scss";
import updateEmployee from "../../../api/updateEmployee";
import { RIGHTS, checkRights } from "../../../const/rightsUtils";

export default function EmployeePage(props) {
  const { uid, name, tasks, projects, availability, email, phone, linkedin, team, surname, securityAccess, username } =
    props.employee;

  const [changeTeam, setChangeTeam] = useState(false);
  const [newTeam, setNewTeam] = useState("");
  const [moreChanges, setMoreChanges] = useState(false);
  const [newUsername, setNewUsername] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newSecurityAccess, setNewSecurityAccess] = useState("");

  const handleDeleteEmployee = async (id) => {
    const deleteResponse = await deleteEmployee(id, getUserData().userId);
    // console.log('response of deletion:', deleteResponse);
    if (deleteResponse.ok) {
      alert("Employee deleted!");
      window.location.reload();
    } else {
      alert("Employee could not be deleted!");
    }
  };

  const handleUpdateEmployee = async (field) => {
    let changes = {};
    switch (field) {
      case "team":
        changes["team"] = newTeam;
        break;
      case "availability":
        changes["availability"] = availability.toLowerCase() === "available" ? "On vacation" : "Available";
        break;
      case "username":
        changes["username"] = newUsername;
        break;
      case "password":
        changes["password"] = hashPassword(newPassword);
        break;
      case "securityAccess":
        changes["securityAccess"] = newSecurityAccess;
        break;
      default:
        console.log("Unknown field for update: ", field);
        return;
    }

    const updateResponse = await updateEmployee(uid, getUserData().userId, changes);

    if (updateResponse) {
      alert("Employee updated!");
      window.location.reload();
    } else {
      alert("Employee could not be updated!");
    }
  };

  return (
    <div className="employeePage">
      <div id="data">
        <div id="employeeCard">
          <h2>{`${name} ${surname} `}</h2>
          <p>Security access: {securityAccess}</p>
        </div>

        {checkRights("delete-user") && (
          <button
            onClick={() => {
              handleDeleteEmployee(uid);
            }}
          >
            Delete user
          </button>
        )}

        <div className="label">
          <span>Team:</span>
          {!changeTeam ? (
            <>
              <span>{team === "" ? "None" : team}</span>
              {checkRights("update-user") && (
                <button
                  onClick={() => {
                    setChangeTeam(!changeTeam);
                  }}
                >
                  change
                </button>
              )}
            </>
          ) : (
            <>
              <input
                onChange={(e) => {
                  setNewTeam(e.target.value);
                }}
              />
              <button
                onClick={() => {
                  handleUpdateEmployee("team");
                }}
              >
                save
              </button>
            </>
          )}
        </div>

        <div className="label">
          <span>Tasks:</span>

          {tasks === "" ? <i>None</i> : tasks.split(",").map((t, idx) => <span key={`task-${idx}`}>{t}</span>)}
        </div>

        <div className="label">
          <span>Projects:</span>
          {projects === "" ? <i>None</i> : projects.split(",").map((p, idx) => <span key={`proj-${idx}`}>{p}</span>)}
        </div>

        <div className="label">
          <span>Availability:</span>
          <span>{availability}</span>

          {checkRights("update-user") && (
            <button
              onClick={() => {
                handleUpdateEmployee("availability");
              }}
            >
              change
            </button>
          )}
        </div>

        {!moreChanges ? (
          <div className="label">
            <button
              onClick={() => {
                setMoreChanges(true);
              }}
            >
              change other
            </button>
          </div>
        ) : (
          <div className="change-other">
            <div className="label">
              <span>Username:</span>
              <input
                defaultValue={username}
                onClick={(e) => {
                  setNewUsername(e.target.value);
                }}
              />
              <button
                onClick={() => {
                  handleUpdateEmployee("username");
                }}
              >
                Save
              </button>
            </div>
            <div className="label">
              <span>Password:</span>
              <input
                type="password"
                onClick={(e) => {
                  setNewPassword(e.target.value);
                }}
              />
              <button
                onClick={() => {
                  handleUpdateEmployee("password");
                }}
              >
                Save
              </button>
            </div>
            <div className="label">
              <span>Security access:</span>
              <select
                defaultValue={securityAccess}
                onClick={(e) => {
                  setNewSecurityAccess(e.target.value);
                }}
              >
                {Object.keys(RIGHTS).map((r) => (
                  <option key={`acces-${r}`} value={r}>
                    {r}
                  </option>
                ))}
              </select>
            </div>
            <button
              onClick={() => {
                handleUpdateEmployee("securityAccess");
              }}
            >
              Save
            </button>
          </div>
        )}
      </div>

      <div id="contact">
        <AvatarSvg />
      </div>
    </div>
  );
}
