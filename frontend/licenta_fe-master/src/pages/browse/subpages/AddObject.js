import React from "react";
import "./AddObject.scss";
import { useMemo } from "react";
import { getUserData } from "../../../const/utils";
import createEmployee from "../../../api/createEmployee";
import { RIGHTS } from "../../../const/rightsUtils";
import createTask from "../../../api/createTask";
import createSprint from "../../../api/createSprint";
import createProject from "../../../api/createProject";

export default function AddObject({ page, allEmployees, allProjects, allSprints, allTasks }) {
  //
  const EmployeesFields = [
    "N-Name",
    "N-Surname",
    "N-Username",
    "N-Password",
    "N-Team",
    "D-Availability-Available-On Vacation",
    "D-SecurityAccess-" + Object.keys(RIGHTS).join("-"),
  ];
  const SprintsFields = [
    "N-Title",
    "N-Team",
    "D-Status-Active-In progress-Closed",
    "D-Project" + allProjects.reduce((aux, spr) => aux + "-" + spr.title, ""),
  ];
  const TasksFields = [
    "N-Title",
    "N-Description",
    "D-Sprint" + allSprints.reduce((aux, spr) => aux + "-" + spr.title, ""),
    "D-Status-Active-In progress-Closed",
    "D-Project" + allProjects.reduce((aux, spr) => aux + "-" + spr.title, ""),
    "D-Assignee" + allEmployees.reduce((aux, spr) => aux + "-" + spr.username, ""),
    "I-StoryPoints",
  ];
  const ProjectsFields = [
    "N-Team",
    "N-Title",
    "N-Description",
    "D-Status-Active-In progress-Closed",
    "D-Hidden-Visible for all-Hidden",
  ];

  console.log(TasksFields);
  const currentPageFields = useMemo(() => {
    switch (page) {
      case "Employees":
        return EmployeesFields;
      case "Sprints":
        return SprintsFields;
      case "Tasks":
        return TasksFields;
      case "Projects":
        return ProjectsFields;
      default:
        return []; // You can specify a default value or handle other cases as needed
    }
  }, [page]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("submit", e);
    console.log(e.target[0].value);

    const dataObject = {};
    currentPageFields.forEach((field, idx) => {
      const fieldName = field.split("-")[1].charAt(0).toLowerCase() + field.split("-")[1].slice(1);
      dataObject[fieldName] = e.target[idx].value;
    });

    let response = null;
    const userId = getUserData().userId;

    switch (page) {
      case "Employees":
        response = await createEmployee(userId, dataObject);
        break;
      case "Sprints":
        response = await createSprint(userId, dataObject);
        break;
      case "Tasks":
        response = await createTask(userId, dataObject);
        break;
      case "Projects":
        response = await createProject(userId, {
          ...dataObject,
          hidden: dataObject.hidden === "Hidden" ? true : false,
        });
        break;
      default:
        return [];
    }

    alert(`New ${page.slice(0, -1)} created!`);
    // window.location.reload();

    console.log(response);
  };

  return (
    <div className="addObjPage">
      <form onSubmit={handleSubmit}>
        <h2>Add New {page.slice(0, -1)} </h2>

        {currentPageFields.map((field, idx) => (
          <label key={`field-${idx}`}>
            {field.split("-")[1]}:
            {field.split("-")[0] === "D" ? (
              <select placeholder={field.split("-")[1]} required defaultValue={field.split("-")[2]}>
                {field
                  .split("-")
                  .slice(2)
                  .map((opt) => (
                    <option value={opt} key={`dropdown-${opt}`}>
                      {opt}
                    </option>
                  ))}
              </select>
            ) : field.split("-")[0] === "I" ? (
              <input
                type="number"
                placeholder={field.split("-")[1]}
                required
                defaultValue={field.split("-")[2]}
                max={50}
              />
            ) : (
              <input placeholder={field.split("-")[1]} required defaultValue={`test-${field.split("-")[1]}`} />
            )}
          </label>
        ))}
        <button>Add</button>
      </form>
    </div>
  );
}
