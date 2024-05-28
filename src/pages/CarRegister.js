import React from "react";
import Header from "../components/Header";
import '../styles/RegisterServices.css';

import "../styles/RegisterServices.css";
import Sidebar from "../components/Sidebar";
import PostCar from "../components/PostCar";

function CarRegister() {
  return (
    <div className="register-services-container">
      <div className="left-sb">
        <Sidebar />
      </div>
      <div className="main-content-services">
        <Header  userName={"Alexandre"} userCircle={"AS"}/>
        <PostCar/>
      </div>
    </div>
  );
}

export default CarRegister;
