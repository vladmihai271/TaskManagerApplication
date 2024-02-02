import React from "react";
import { checkRights } from "../../const/rightsUtils";

export default function ObjectList({ title, children, setAddObjectPage }) {
  const computeTitleForCheckRights = "add-" + (title.slice(0, 1).toLowerCase() + title.slice(1)).slice(0, -1);

  return (
    <div id="objectsList">
      {title && (
        <div className="titleStuff">
          <h3>{title}</h3>
          {checkRights(computeTitleForCheckRights) && (
            <button
              id="addButton"
              onClick={() => {
                setAddObjectPage(title);
              }}
            >
              +
            </button>
          )}
        </div>
      )}

      {/* <input placeholder="search by name" /> */}
      <div id="actual-list">{children}</div>
    </div>
  );
}
