import React, { createContext, useContext } from "react";

const EndpointContext = createContext();

export const EndpointProvider = ({ children }) => {
  const endpoint = "http://localhost:8080";
  //Trocar o endpoint aqui no e services/userService.js!
   //https://autolog-deploy.azurewebsites.net
   //apenas o endpoint padrao

  return (
    <EndpointContext.Provider value={endpoint}>
      {children}
    </EndpointContext.Provider>
  );
};

export const useEndpoint = () => {
  return useContext(EndpointContext);
};
