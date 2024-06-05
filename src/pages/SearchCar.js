
import React, { useState } from "react";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";

import "../styles/Dashboard.css";
import TableCar from "../components/TableCar";

function SearchCar() {
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
        
        <TableCar />
      </div>
    </div>
  );
}

export default SearchCar;
