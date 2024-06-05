import React from "react";
import Header from "../components/Header";
import EditUser from "../components/EditUser";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import "../styles/ConfigUser.css";

function ConfigUser() {
  return (
    <div className="config-container">
      <Header />
          <div className="form-section">
            <EditUser />
        </div>
      
      <Link to="/dashboard" className="nav-button">
        <FontAwesomeIcon icon={faArrowLeft} className="icon" /> Voltar para Dashboard
      </Link>
    </div>
  );
}

export default ConfigUser;
