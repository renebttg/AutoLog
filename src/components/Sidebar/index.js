import React from "react";
import "./styles.css";
import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../../services/AuthContext";
import Logo from "../Logo";

function Sidebar({ active }) {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="sidebar">
      <div className="logo">
        <Logo titleColor="#0A0E14" barColor="#fff" />
      </div>
      <div className="section">
        <ul className="links">
          <li>
            <NavLink
              to="/dashboard"
              className={`button-link ${
                active === "dashboard" ? "active" : ""
              }`}
            >
              Dashboard
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/carRegister"
              className={`button-link ${
                active === "carRegister" ? "active" : ""
              }`}
            >
              Registro de Veículos
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/servicesRegister"
              className={`button-link ${
                active === "servicesRegister" ? "active" : ""
              }`}
            >
              Registro de Serviços
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/historic"
              className={`button-link ${active === "historic" ? "active" : ""}`}
            >
              Histórico
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/editService"
              className={`button-link ${
                active === "editService" ? "active" : ""
              }`}
            >
              Controle de Serviços
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/searchCar"
              className={`button-link ${
                active === "searchCar" ? "active" : ""
              }`}
            >
              Buscar Veiculo
            </NavLink>
          </li>
        </ul>
      </div>

      <div className="section">
        <ul className="links">
          <li>
            <button onClick={handleLogout} className="button-link">
              Sair
            </button>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default Sidebar;
