import React from "react";
import { Link } from "react-router-dom";

function SidebarConfigUser({ title, options }) {
  return (
    <div className="sidebar">
      <h2>{title}</h2>
      <ul className="sidebar-menu">
        {options.map((option, index) => (
          <li key={index}>
            <Link to={option.link}>{option.label}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default SidebarConfigUser;
