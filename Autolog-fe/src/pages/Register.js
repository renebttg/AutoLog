import React from "react";
import FormRegister from "../components/FormRegister";

import '../styles/Register.css';
import imgRegister from '../assets/imgs/ia-car2.jpg'



function Register() {
  return (
    <div className="container-register">
      <FormRegister/>
      <div  className="img-register">
      <img src={imgRegister} alt="Imagem de registro" />
      </div>
    
      
    </div>
  );
}

export default Register;
