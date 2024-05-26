import React from "react";
import Header from "../components/Header";
import '../styles/RegisterServices.css';

import Sidebar from "../components/Sidebar";
import PostService from "../components/PostService";

function ServiceRegister() {
  return (
    <div className="register-services-container">
      <div className="left-sb">
        <Sidebar />
      </div>
      <div className="main-content-services">
        <Header userName={"Alexandre"} userCircle={"AS"} />
        <PostService />
      </div>
    </div>
  );
}

export default ServiceRegister;
