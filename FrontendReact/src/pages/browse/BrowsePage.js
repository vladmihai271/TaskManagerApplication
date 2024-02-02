import "./BrowsePage.scss";
import EmployeePage from "./subpages/EmployeePage";
import ObjectList from "./ObjectList";
import { useEffect, useState } from "react";
import TaskPage from "./subpages/TaskPage";
import SprintPage from "./subpages/SprintPage";
import ProjectPage from "./subpages/ProjectPage";
import request from "../../api/request";
import AddObject from "./subpages/AddObject";
import { getUserData, setCredentialsToLocalStorage } from "../../const/utils";
import { useNavigate } from "react-router-dom";

function BrowsePage() {
  const [page, setPage] = useState("");
  const [subPage, setSubPage] = useState({
    pageType: undefined,
    data: undefined,
  });

  const [employeeList, setEmployeeList] = useState([]);
  const [taskList, setTaskList] = useState([]);
  const [sprintList, setSprintList] = useState([]);
  const [projectList, setProjectList] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    const userId = getUserData().userId;
    if (userId === "") {
      navigate("/login");
      return;
    }

    async function fetchdata() {
      const userId = getUserData().userId;
      if (userId !== "") {
        console.log("id: ", userId);
        setEmployeeList(await request(`employees/${userId}`));
        setTaskList(await request(`tasks/${userId}`));
        setSprintList(await request(`sprints/${userId}`));
        setProjectList(await request(`projects/${userId}`));
      } else {
        alert("You are not logged in!");
      }
    }

    fetchdata();
  }, []);

  const changePage = (newPage) => {
    setPage(newPage);
  };

  const RenderObjectPageChildren = () => {
    switch (page) {
      case "Employees":
        return employeeList.map((e, idx) => (
          <button
            key={`Employee-${idx}`}
            onClick={() => {
              setSubPage({
                pageType: "Employee",
                data: e,
              });
            }}
          >
            {e.username}
          </button>
        ));
      case "Tasks":
        return taskList.map((t, idx) => (
          <button
            key={`Task-${idx}`}
            onClick={() => {
              setSubPage({
                pageType: "Task",
                data: t,
              });
            }}
          >
            {t.title}
          </button>
        ));
      case "Sprints":
        return sprintList.map((s, idx) => (
          <button
            key={`Sprint-${idx}`}
            onClick={() => {
              setSubPage({
                pageType: "Sprint",
                data: s,
              });
            }}
          >
            {s.title}
          </button>
        ));
      case "Projects":
        return projectList.map((p, idx) => (
          <button
            key={`Project-${idx}`}
            onClick={() => {
              setSubPage({
                pageType: "Project",
                data: p,
              });
            }}
          >
            {p.title}
          </button>
        ));
      default:
        return null;
    }
  };

  const RenderSubPage = () => {
    if (!subPage.pageType) return null;

    switch (subPage.pageType) {
      case "Employee":
        return <EmployeePage employee={subPage.data} />;
      case "Task":
        return <TaskPage task={subPage.data} />;
      case "Sprint":
        return <SprintPage sprint={subPage.data} />;
      case "Project":
        return <ProjectPage project={subPage.data} />;
      case "ADD-OBJ":
        console.log(employeeList);
        return (
          <AddObject
            page={subPage.data}
            allEmployees={employeeList}
            allProjects={projectList}
            allSprints={sprintList}
            allTasks={taskList}
          />
        );
      default:
        return null;
    }
  };

  const handleLogOut = () => {
    setCredentialsToLocalStorage("", "", "", "");
    window.location.reload();
  };

  return (
    <div className="browsePageContainer">
      <header>
        <button onClick={handleLogOut}>Log out</button>
      </header>

      <div className="objects">
        <div id="objectTypesList">
          <h3>Pages</h3>
          <button
            onClick={() => {
              changePage("Tasks");
              setSubPage({ pageType: undefined });
            }}
          >
            Tasks
          </button>
          <button
            onClick={() => {
              changePage("Employees");
              setSubPage({ pageType: undefined });
            }}
          >
            Employees
          </button>

          <button
            onClick={() => {
              changePage("Sprints");
              setSubPage({ pageType: undefined });
            }}
          >
            Sprints
          </button>

          <button
            onClick={() => {
              changePage("Projects");
              setSubPage({ pageType: undefined });
            }}
          >
            Projects
          </button>
        </div>

        <ObjectList
          title={page}
          setAddObjectPage={(whichObject) => {
            setSubPage({ pageType: "ADD-OBJ", data: whichObject });
          }}
        >
          {RenderObjectPageChildren()}
        </ObjectList>

        <div id="objectPage">
          <RenderSubPage />
        </div>
      </div>
    </div>
  );
}

export default BrowsePage;
