import React from "react";
import DeleteService from "../components/DeleteService";
import DeleteCar from "../components/DeleteCar";
import Header from "../components/Header";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import "../styles/ConfigUser.css";

function Config() {
  return (
    <div className="config-container">
      <Header />
      <div className="content-wrapper">
        <div className="config-area">
          <div className="form-section">
            <h2 className="section-title">Excluir Serviço</h2>
            <DeleteService />
          </div>
          <div className="form-section">
            <h2 className="section-title">Excluir Carro</h2>
            <DeleteCar />
          </div>
        </div>
      </div>
      
        <Link to="/dashboard" className="nav-button">
          <FontAwesomeIcon icon={faArrowLeft} className="icon" /> Voltar para Dashboard
        </Link>
    </div>
  );
}

export default Config;
