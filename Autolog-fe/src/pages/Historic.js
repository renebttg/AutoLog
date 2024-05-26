
import React, { useState } from "react";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

import "../styles/Dashboard.css";
import TableHistoric from "../components/TableHistoric";

function Historic() {
  const [isDarkMode, setIsDarkMode] = useState(false);

  const toggleDarkMode = () => {
    setIsDarkMode(!isDarkMode);
  };

  return (
    <div className={`dashboard-container ${isDarkMode ? 'dark-mode' : ''}`}>
      <div className="left-sb">
        <Sidebar />
      </div>
      <div className="main-content">
        <Header userName={"Alexandre"} userCircle="AS" toggleDarkMode={toggleDarkMode} /> 
        
        <TableHistoric />
      </div>
    </div>
  );
}

export default Historic;
