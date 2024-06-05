import axios from "axios";

export default class UserServices {
  constructor() {
    this.axios = axios.create({
      baseURL: process.env.REACT_APP_API_LOGIN || "http://localhost:8080/auth",
    });
  }
  //Trocar o endpoint aqui no e services/EndpointContext.js!
  //https://autolog-deploy.azurewebsites.net/auth
  


  async login(dados) {
    try {
      const { data } = await this.axios.post("/login", dados);

      console.log("Login response:", data);

      if (data && data.token) {
        localStorage.setItem(
          "user",
          JSON.stringify({
            email: data.email,
            token: data.token,
          })
        );
        console.log("Token armazenado:", data.token);
        return true;
      } else {
        console.log("Nenhum token recebido");
        return false;
      }
    } catch (error) {
      console.error("Erro durante o login:", error);
      return false;
    }
  }
}
