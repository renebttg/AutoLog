import React from "react";

import '../styles/Register.css';
import imgRegister from '../assets/imgs/MechanicWorkshop.png.jpg'
import ForgotPassword from "../components/formForgotPassword";



function passwordSection() {
  return (
    <div className="container-register">
        <ForgotPassword/>
      <div  className="img-register">
      <img src={imgRegister} alt="Imagem de registro" />
      </div>
    
      
    </div>
  );
}

export default passwordSection;
