import React, { useState, useEffect } from "react";
import axios from "axios";
import Input from "../Inputs";
import DecodificarToken from "../../services/tokenDecode";
import "./styles.css";

function EditUser() {
  const [formData, setFormData] = useState({
    name: "",
    cnpj: "",
    email: "",
    phone: "",
    password: "",
    nameWorkshop: "",
    addressWorkshop: "",
  });

  const [message, setMessage] = useState("");
  const [, setIsLoading] = useState(true);
  const [userId, setUserId] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const fetchUserData = async (userId) => {
    const token = JSON.parse(localStorage.getItem("user"))?.token;
    if (!token) {
      setMessage("Erro de autenticação. Por favor, faça login novamente.");
      setIsLoading(false);
      return;
    }

    try {
      const response = await axios.get(
        `http://localhost:8080/users/${userId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const userData = response.data;
      setFormData({
        name: userData.name,
        cnpj: userData.cnpj,
        email: userData.email,
        phone: userData.phone,
        password: userData.password,
        nameWorkshop: userData.nameWorkshop,
        addressWorkshop: userData.addressWorkshop,
      });
      setIsLoading(false);
    } catch (error) {
      console.error("Erro ao buscar informações do usuário:", error);
      setMessage("Erro ao buscar informações do usuário.");
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (userId) {
      fetchUserData(userId);
    }
  }, [userId]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = JSON.parse(localStorage.getItem("user"))?.token;
    if (!token) {
      setMessage("Erro de autenticação. Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await axios.put(
        `https://autolog-deploy.azurewebsites.net/users/${userId}`,
        formData,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 200) {
        setMessage("Informações do usuário atualizadas com sucesso!");
      } else {
        setMessage("Erro ao atualizar as informações do usuário.");
      }
    } catch (error) {
      console.error("Erro ao atualizar informações do usuário:", error);
      setMessage("Erro ao atualizar as informações do usuário.");
    }
  };

  const handleTokenDecoded = (id) => {
    setUserId(id);
  };

  return (
    <div className="edit-user-container">
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <form onSubmit={handleSubmit} className="form">
        <h1 className="form-title">Editar Informações do Usuário</h1>
        <Input
          labelText="Nome"
          labelFor="name"
          inputType="text"
          onChange={handleInputChange}
          value={formData.name}
          name="name"
          placeholder="Digite o nome"
          autoComplete="on"
        />
        <Input
          labelText="CNPJ"
          labelFor="cnpj"
          inputType="text"
          onChange={handleInputChange}
          value={formData.cnpj}
          name="cnpj"
          placeholder="Digite o CNPJ"
          autoComplete="on"
        />
        <Input
          labelText="Email"
          labelFor="email"
          inputType="email"
          onChange={handleInputChange}
          value={formData.email}
          name="email"
          placeholder="Digite o email"
          autoComplete="on"
        />
        <Input
          labelText="Telefone"
          labelFor="phone"
          inputType="text"
          onChange={handleInputChange}
          value={formData.phone}
          name="phone"
          placeholder="Digite o telefone"
          autoComplete="on"
        />
        <Input
          labelText="Senha"
          labelFor="password"
          inputType="password"
          onChange={handleInputChange}
          value={formData.password}
          name="password"
          placeholder="Digite a senha"
          autoComplete="on"
        />
        <Input
          labelText="Nome da Oficina"
          labelFor="nameWorkshop"
          inputType="text"
          onChange={handleInputChange}
          value={formData.nameWorkshop}
          name="nameWorkshop"
          placeholder="Digite o nome da oficina"
          autoComplete="on"
        />
        <Input
          labelText="Endereço da Oficina"
          labelFor="addressWorkshop"
          inputType="text"
          onChange={handleInputChange}
          value={formData.addressWorkshop}
          name="addressWorkshop"
          placeholder="Digite o endereço da oficina"
          autoComplete="on"
        />
        <div className="btn-container">
          <button type="submit" className="submit-btn">
            Atualizar Informações
          </button>
        </div>
        {message && <p className="form-message">{message}</p>}
      </form>
    </div>
  );
}

export default EditUser;
