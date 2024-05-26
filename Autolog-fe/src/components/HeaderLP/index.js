import React from "react";
import { useNavigate } from "react-router-dom";

import "./styles.css";
import Logo from "../Logo";

const HeaderLP = () => {
  const navigate = useNavigate();

  const handleLoginClick = () => {
    navigate("/login");
  };
  return (
    <header className="header">
      <div>
        <Logo titleColor="#fff" barColor="#fff" />
      </div>
      <div>
        <nav className="nav">
          <ul>
            <li>
              <a href="#home">Home</a>
            </li>
            <li>
              <a href="#features">Recursos</a>
            </li>
            <li>
              <a href="#project">Projeto</a>
            </li>
            <li>
              <a href="#about">Sobre</a>
            </li>
            <li>
              <a href="#contact">Contato</a>
            </li>
          </ul>
        </nav>
      </div>
      <div>
        <button className="btn-header" onClick={handleLoginClick}>
          Login
        </button>
      </div>
    </header>
  );
};

export default HeaderLP;
