import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import "../styles/Dashboard.css";
import Cards from "../components/Cards";
import TableServices from "../components/TableServices";
import ServicesToday from "../utils/servicesToday";
import MonthlyServiceValue from "../utils/monthlyServiceValue";
import ServicesCompletedToday from "../utils/ServicesCompletedToday";
import ShowLastCar from "../utils/showLastCar";
import {
  faCalendarDay,
  faDollarSign,
  faCheckCircle,
  faCar,
} from "@fortawesome/free-solid-svg-icons";

function Dashboard() {
  const [isDarkMode, setIsDarkMode] = useState(false);
  const navigate = useNavigate();

  const toggleDarkMode = () => {
    setIsDarkMode(!isDarkMode);
  };

  const handleViewCompletedServices = () => {
    navigate("/historic");
  };

  const handleNavigateRegister = () => {
    navigate('/servicesRegister')
  }

  return (
    <div className={`dashboard-container ${isDarkMode ? "dark-mode" : ""}`}>
      <div className="left-sb">
        <Sidebar />
      </div>
      <div className="main-content">
        <Header
          userName={"Alexandre"}
          userCircle="AS"
          toggleDarkMode={toggleDarkMode}
        />
        <div className="cardSection">
          <Cards
            title="Veículo Registrado Recentemente"
            value={<ShowLastCar/>}
            icon={faCar}
            buttonLabel="Registrar serviço"
            onButtonClick={handleNavigateRegister}
            blackCard={true}
          />
          <Cards
            title={"Serviços do Dia"}
            value={<ServicesToday />}
            icon={faCalendarDay}
            subText={<span className="card-subtext"></span>}
          />
          <Cards
            title={"Valores do Mês"}
            value={<MonthlyServiceValue />}
            icon={faDollarSign}
            subText={<span className="card-subtext"></span>}
          />
          <Cards
            title={"Serviços Concluídos"}
            value={<ServicesCompletedToday />}
            icon={faCheckCircle}
            buttonLabel={"Visualizar"}
            onButtonClick={handleViewCompletedServices}
          />
        </div>
        <TableServices />
      </div>
    </div>
  );
}

export default Dashboard;
