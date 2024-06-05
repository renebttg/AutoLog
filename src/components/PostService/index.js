import React, { useState, useEffect } from "react";
import Input from "../Inputs";
import axios from "axios";
import DecodificarToken from "../../services/tokenDecode";
import { useEndpoint } from "../../services/EndpointContext";


function PostService() {
  const [formData, setFormData] = useState({
    licencePlate: "",
    serviceDescription: "",
    serviceValue: "",
    serviceDate: "",
  });

  const [message, setMessage] = useState("");
  const [carId, setCarId] = useState(null);
  const [userId, setUserId] = useState(null);
  const endpoint = useEndpoint();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  useEffect(() => {
    const fetchCarIdByPlate = async (userId) => {
      const token = JSON.parse(localStorage.getItem("user"))?.token;
      if (!token) {
        setMessage("Erro de autenticação. Por favor, faça login novamente.");
        return;
      }

      try {
        const response = await axios.get(
          `${endpoint}/users/${userId}/cars`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const cars = response.data;
        const targetCar = cars.find(
          (car) => car.licencePlate === formData.licencePlate
        );
        if (targetCar) {
          setCarId(targetCar.idCar);
        } else {
          setCarId(null);
          console.error("Carro não encontrado");
        }
      } catch (error) {
        console.error("Erro ao buscar informações do carro:", error);
      }
    };

    if (formData.licencePlate && userId) {
      fetchCarIdByPlate(userId);
    }
  }, [formData.licencePlate, userId, endpoint]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!carId) {
      setMessage("Placa do veículo não encontrada");
      return;
    }

    const token = JSON.parse(localStorage.getItem("user"))?.token;
    if (!token) {
      setMessage("Erro de autenticação. Por favor, faça login novamente.");
      return;
    }

    try {
      const dataToSend = {
        ...formData,
        repairDate: formData.serviceDate,
        serviceStatus: "Aguardando Início",
      };

      const response = await fetch(
        `${endpoint}/users/${userId}/cars/${carId}/maintenance`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(dataToSend),
        }
      );

      if (response.ok) {
        setMessage("Serviço registrado com sucesso!");
      } else {
        setMessage("Erro ao enviar serviço");
      }
    } catch (error) {
      console.error("Erro ao adicionar serviço:", error.message);
      setMessage("Erro ao enviar serviço");
    }

    setFormData({
      licencePlate: "",
      serviceDescription: "",
      serviceValue: "",
      serviceDate: "",
    });

    setTimeout(() => {
      window.location.reload();
    }, 2000);
  };

  const handleTokenDecoded = (id) => {
    setUserId(id);
  };

  return (
    <div className="post-service">
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <form onSubmit={handleSubmit} className="form">
        <h1 className="form-title">Digite os dados do serviço</h1>
        <Input
          labelText="Placa do Veículo"
          labelFor="licencePlate"
          inputType="text"
          onChange={handleInputChange}
          value={formData.licencePlate}
          name="licencePlate"
          placeholder="Digite a placa do veículo"
          autoComplete="on"
          required
        />
        <Input
          labelText="Descrição do Serviço"
          labelFor="serviceDescription"
          inputType="text"
          onChange={handleInputChange}
          value={formData.serviceDescription}
          name="serviceDescription"
          placeholder="Digite a descrição do serviço"
          autoComplete="on"
          required
        />
        <Input
          labelText="Valor do Serviço"
          labelFor="serviceValue"
          inputType="number"
          onChange={handleInputChange}
          value={formData.serviceValue}
          name="serviceValue"
          placeholder="Digite o valor do serviço"
          required
        />
        <Input
          labelText="Data do Serviço"
          labelFor="serviceDate"
          inputType="date"
          onChange={handleInputChange}
          value={formData.serviceDate}
          name="serviceDate"
          placeholder="Selecione a data do serviço"
          required
        />
        <div className="btn-container">
          <button type="submit" className="submit-button">
            Registrar Serviço
          </button>
        </div>
        {message && <p className="form-message">{message}</p>}
      </form>
    </div>
  );
}

export default PostService;
