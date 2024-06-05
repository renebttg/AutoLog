import React, { useState } from "react";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faMoon,
  faSyncAlt,
  faCog,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import "./styles.css";
import DecodificarToken from "../../services/tokenDecode";
import { useEndpoint } from "../../services/EndpointContext";

function Header({ toggleDarkMode }) {
  const [workshopName, setWorkshopName] = useState("Carregando...");
  const [userCircleInitials, setUserCircleInitials] = useState("");
  const [showOptions, setShowOptions] = useState(false);
  const endpoint = useEndpoint(); // Usa o endpoint do contexto

  const fetchWorkshopName = async (userId) => {
    try {
      const response = await axios.get(`${endpoint}/users/${userId}`);
      const nameWorkshop = response.data.nameWorkshop;
      setWorkshopName(nameWorkshop);
      setUserCircleInitials(getInitials(nameWorkshop));
    } catch (error) {
      setWorkshopName("AutoLog");
    }
  };

  const handleTokenDecoded = (id) => {
    fetchWorkshopName(id);
  };

  const getCurrentDate = () => {
    const days = [
      "Domingo",
      "Segunda",
      "Terça",
      "Quarta",
      "Quinta",
      "Sexta",
      "Sábado",
    ];
    const months = [
      "Janeiro",
      "Fevereiro",
      "Março",
      "Abril",
      "Maio",
      "Junho",
      "Julho",
      "Agosto",
      "Setembro",
      "Outubro",
      "Novembro",
      "Dezembro",
    ];
    const currentDate = new Date();
    const dayOfWeek = days[currentDate.getDay()];
    const month = months[currentDate.getMonth()];
    const dayOfMonth = currentDate.getDate();
    return `${dayOfWeek}, ${dayOfMonth} ${month}`;
  };

  const handleRefreshClick = () => {
    window.location.reload();
  };

  const getInitials = (name) => {
    const nameArray = name.split(" ");
    return nameArray
      .map((word) => word.charAt(0))
      .join("")
      .toUpperCase();
  };

  return (
    <div className="header-container">
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <div className="user-name-presentation">
        <h1>{workshopName}</h1>
      </div>
      <div className="header-tools">
        <div className="header-date">{getCurrentDate()}</div>
        <div className="header-icons">
          <FontAwesomeIcon
            icon={faMoon}
            className="header-icon"
            onClick={toggleDarkMode}
          />
          <FontAwesomeIcon
            icon={faSyncAlt}
            className="header-icon"
            onClick={handleRefreshClick}
          />
        </div>
        <div className="header-user-config">
          <div className="dropdown">
            <div
              className="header-user-circle"
              onClick={() => setShowOptions(!showOptions)}
            >
              {userCircleInitials}
            </div>
            {showOptions && (
              <div className="dropdown-content">
                <div className="dropdown-option">
                  <Link to="/configUser" className="link-header">
                    <div>
                      <FontAwesomeIcon
                        icon={faUser}
                        className="dropdown-icon"
                      />
                      <span>Configurações de Usuário</span>
                    </div>
                  </Link>
                </div>
                <div className="dropdown-option">
                  <Link to="/config" className="link-header">
                    <div>
                      <FontAwesomeIcon icon={faCog} className="dropdown-icon" />
                      <span>Configurações</span>
                    </div>
                  </Link>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Header;
