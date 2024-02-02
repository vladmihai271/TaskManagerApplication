export const getUserRights = () => {
  return localStorage.getItem("rights");
};

export const RIGHTS = {
  HR: {
    addEmployee: true,
    deleteUser: true,
    updateUser: true,
    addTask: true,
    addSprint: false,
    updateSprint: false,
    deleteSprint: false,
    addProject: false,
    updateProject: false,
    deleteProject: false,
  },
  "Team Leader": {
    addEmployee: false,
    deleteUser: false,
    updateUser: false,
    addTask: true,
    addSprint: true,
    updateSprint: true,
    deleteSprint: true,
    addProject: false,
    updateProject: false,
    deleteProject: false,
  },
  "Department Chief": {
    addEmployee: false,
    deleteUser: false,
    updateUser: false,
    addTask: true,
    addSprint: true,
    updateSprint: true,
    deleteSprint: true,
    addProject: true,
    updateProject: true,
    deleteProject: true,
  },
  Employee: {
    addEmployee: false,
    deleteUser: false,
    updateUser: false,
    addTask: true,
    addSprint: false,
    updateSprint: false,
    deleteSprint: false,
    addProject: false,
    updateProject: false,
    deleteProject: false,
  },
};

export const checkRights = (action) => {
  switch (action) {
    case "add-employee":
      return RIGHTS[getUserRights()]?.addEmployee;
    case "delete-user":
      return RIGHTS[getUserRights()]?.deleteUser;
    case "update-user":
      return RIGHTS[getUserRights()]?.updateUser;
    case "add-task":
      return RIGHTS[getUserRights()]?.addTask;
    case "add-project":
      return RIGHTS[getUserRights()]?.addProject;
    case "update-project":
      return RIGHTS[getUserRights()]?.updateProject;
    case "delete-project":
      return RIGHTS[getUserRights()]?.deleteProject;
    case "add-sprint":
      return RIGHTS[getUserRights()]?.addSprint;
    case "update-sprint":
      return RIGHTS[getUserRights()]?.updateSprint;
    case "delete-sprint":
      return RIGHTS[getUserRights()]?.deleteSprint;
    default:
      console.log("Unknown action for rights: ", action);
      return null;
  }
};
