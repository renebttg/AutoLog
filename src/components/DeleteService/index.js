import React, { useState, useEffect } from "react";
import axios from "axios";
import Input from "../Inputs";
import DecodificarToken from "../../services/tokenDecode";

function DeleteService() {
  const [formData, setFormData] = useState({
    plateNumber: "",
    idMaintenance: "",
  });

  const [message, setMessage] = useState("");
  const [carId, setCarId] = useState(null);
  const [userId, setUserId] = useState(null);
  const [step, setStep] = useState(1);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  useEffect(() => {
    const fetchCarIdByPlate = async () => {
      const token = JSON.parse(localStorage.getItem("user"))?.token;
      if (!token) {
        console.error("Erro de autenticação. Por favor, faça login novamente.");
        return;
      }

      try {
        const response = await axios.get(
          `https://autolog-deploy.azurewebsites.net/users/${userId}/cars`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const cars = response.data;
        const targetCar = cars.find(
          (car) => car.licencePlate === formData.plateNumber
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

    if (formData.plateNumber && userId) {
      fetchCarIdByPlate();
    }
  }, [formData.plateNumber, userId]);

  const handleNextStep = (e) => {
    e.preventDefault();
    if (!formData.plateNumber || !formData.idMaintenance) {
      setMessage("Por favor, preencha todos os campos.");
    } else {
      setMessage("");
      setStep(2);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!carId) {
      setMessage("Placa do veículo não encontrada");
      return;
    }

    const token = JSON.parse(localStorage.getItem("user"))?.token;
    if (!token) {
      console.error("Erro de autenticação. Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await fetch(
        `https://autolog-deploy.azurewebsites.net/users/${userId}/cars/${carId}/maintenance/${formData.idMaintenance}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.ok) {
        setMessage("Serviço deletado com sucesso!");
      } else {
        setMessage("Erro ao deletar serviço");
      }
    } catch (error) {
      console.error("Erro ao deletar serviço:", error.message);
      setMessage("Erro ao deletar serviço");
    }

    setFormData({
      plateNumber: "",
      idMaintenance: "",
    });

    setTimeout(() => {
      window.location.reload();
    }, 2000);
  };

  const handleTokenDecoded = (id) => {
    setUserId(id);
  };

  return (
    <div className="delete-service">
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <form
        onSubmit={step === 1 ? handleNextStep : handleSubmit}
        className="form"
      >
        <h1 className="form-title">Deletar Serviço</h1>
        {step === 1 && (
          <>
            <Input
              labelText="Placa do Veículo"
              labelFor="plateNumber"
              inputType="text"
              onChange={handleInputChange}
              value={formData.plateNumber}
              name="plateNumber"
              placeholder="Digite a placa do veículo"
              autoComplete="on"
            />
            <Input
              labelText="Número do Serviço"
              labelFor="idMaintenance"
              inputType="number"
              onChange={handleInputChange}
              value={formData.idMaintenance}
              name="idMaintenance"
              placeholder="Digite o número do serviço"
            />
            <div className="btn-container">
              <button type="submit" className="submit-btn">
                Próxima Etapa
              </button>
            </div>
          </>
        )}

        {step === 2 && (
          <div className="btn-container">
            <button type="submit" className="submit-btn">
              Deletar Serviço
            </button>
          </div>
        )}
        {message && <p className="form-message">{message}</p>}
      </form>
    </div>
  );
}

export default DeleteService;
