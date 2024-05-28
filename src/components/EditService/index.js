import React, { useState } from "react";
import axios from "axios";
import Input from "../Inputs";
import Select from "../Inputs/Select";
import DecodificarToken from "../../services/tokenDecode";

function EditService() {
  const [formData, setFormData] = useState({
    licencePlate: "",
    idMaintenance: "",
    serviceDescription: "",
    serviceValue: "",
    serviceStatus: "",
    repairDate: "",
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

  const fetchServiceData = async (userId) => {
    const token = JSON.parse(localStorage.getItem("user"))?.token;
    if (!token) {
      setMessage("Erro de autenticação. Por favor, faça login novamente.");
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
      const maintenanceId = parseInt(formData.idMaintenance);

      let targetCar = null;
      let targetMaintenance = null;

      for (const car of cars) {
        if (car.licencePlate === formData.licencePlate) {
          const maintenance = car.maintenanceHistory.find(
            (m) => m.idMaintenance === maintenanceId
          );
          if (maintenance) {
            targetCar = car;
            targetMaintenance = maintenance;
            break;
          }
        }
      }

      if (targetCar && targetMaintenance) {
        setCarId(targetCar.idCar);
        setFormData((prevFormData) => ({
          ...prevFormData,
          serviceDescription: targetMaintenance.serviceDescription,
          serviceValue: targetMaintenance.serviceValue,
          serviceStatus: targetMaintenance.serviceStatus,
          repairDate: targetMaintenance.repairDate,
        }));
        setStep(2);
      } else {
        setMessage(
          "Carro ou manutenção com o ID especificado não encontrados."
        );
      }
    } catch (error) {
      console.error("Erro ao buscar informações do carro e manutenção:", error);
      setMessage("Erro ao buscar informações do carro e manutenção.");
    }
  };

  const handleNextStep = async (e) => {
    e.preventDefault();
    if (!formData.licencePlate || !formData.idMaintenance) {
      setMessage("Por favor, preencha todos os campos.");
    } else {
      setMessage("");
      await fetchServiceData(userId);
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
      setMessage("Erro de autenticação. Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await fetch(
        `https://autolog-deploy.azurewebsites.net/users/${userId}/cars/${carId}/maintenance/${formData.idMaintenance}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(formData),
        }
      );

      if (response.ok) {
        setMessage("Serviço atualizado com sucesso!");
      } else {
        setMessage("Erro ao atualizar serviço");
      }
    } catch (error) {
      console.error("Erro ao atualizar serviço:", error.message);
      setMessage("Erro ao atualizar serviço");
    }

    setFormData({
      licencePlate: "",
      idMaintenance: "",
      serviceDescription: "",
      serviceValue: "",
      serviceStatus: "",
      repairDate: "",
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
      <form
        onSubmit={step === 1 ? handleNextStep : handleSubmit}
        className="form"
      >
        <h1 className="form-title">Atualização de Serviços</h1>
        {step === 1 && (
          <>
            <Input
              labelText="Placa do Veículo"
              labelFor="licencePlate"
              inputType="text"
              onChange={handleInputChange}
              value={formData.licencePlate}
              name="licencePlate"
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
          <>
            <Input
              labelText="Descrição"
              labelFor="serviceDescription"
              inputType="text"
              onChange={handleInputChange}
              value={formData.serviceDescription}
              name="serviceDescription"
              placeholder="Digite a descrição do serviço"
              autoComplete="on"
            />
            <Input
              labelText="Valor do Serviço"
              labelFor="serviceValue"
              inputType="number"
              onChange={handleInputChange}
              value={formData.serviceValue}
              name="serviceValue"
              placeholder="Digite o valor do serviço"
            />
            <Input
              labelText="Data"
              labelFor="repairDate"
              inputType="date"
              onChange={handleInputChange}
              value={formData.repairDate}
              name="repairDate"
              placeholder="Digite a data da manutenção"
              required
            />
            <Select
              labelText="Status"
              name="serviceStatus"
              value={formData.serviceStatus}
              onChange={handleInputChange}
              options={[
                { value: "Aguardando Início", label: "Aguardando Início" },
                { value: "Em andamento", label: "Em andamento" },
                { value: "Concluído", label: "Concluído" },
              ]}
            />
            <div className="btn-container">
              <button type="submit" className="submit-btn">
                Atualizar Serviço
              </button>
            </div>
          </>
        )}
        {message && <p className="form-message">{message}</p>}
      </form>
    </div>
  );
}

export default EditService;
