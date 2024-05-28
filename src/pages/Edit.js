
import React, { useState } from "react";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

import "../styles/Dashboard.css";
import EditService from "../components/EditService";

function Edit() {
  const [isDarkMode, setIsDarkMode] = useState(false);

  const toggleDarkMode = () => {
    setIsDarkMode(!isDarkMode);
  };

  return (
    <div className={`dashboard-container ${isDarkMode ? 'dark-mode' : ''}`}>
      <div className="left-sb">
        <Sidebar />
      </div>
      <div className="main-content-services">
        <Header userName={"Alexandre"} userCircle="AS" toggleDarkMode={toggleDarkMode} /> 
        
        <EditService/>
      </div>
    </div>
  );
}

export default Edit;
