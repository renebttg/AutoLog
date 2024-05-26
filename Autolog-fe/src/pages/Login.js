import React from "react";
import FormLogin from "../components/FormLogin";

import '../styles/Register.css';
import imgRegister from '../assets/imgs/blur-car.jpg'



function Login() {
  return (
    <div className="container-register">
      <FormLogin/>
      <div  className="img-register">
      <img src={imgRegister} alt="Imagem de registro" />
      </div>
    
      
    </div>
  );
}

export default Login;
