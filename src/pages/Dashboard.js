import React, { useState } from "react";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import "../styles/Dashboard.css";
import Cards from "../components/Cards";
import TableServices from "../components/TableServices";
import ServicesToday from "../utils/servicesToday";
import MonthlyServiceValue from "../utils/monthlyServiceValue";
import ServicesCompletedToday from "../utils/ServicesCompletedToday";

function Dashboard() {
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
        <div className="cardSection">
          <Cards title={"Serviços do Dia"} value={<ServicesToday />} />
          <Cards title={"Valores do Mês"} value={<MonthlyServiceValue/>} />
          <Cards title={"Serviços Concluídos"} value={<ServicesCompletedToday/> } />
        </div>
        <TableServices />
      </div>
    </div>
  );
}

export default Dashboard;
