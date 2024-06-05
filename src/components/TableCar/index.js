import React, { useState, useCallback } from "react";
import axios from "axios";
import DecodificarToken from "../../services/tokenDecode";
import { useEndpoint } from "../../services/EndpointContext";
import "./styles.css";

function TableCar() {
  const [carServices, setCarServices] = useState([]);
  const [searched, setSearched] = useState(false);
  const [licencePlate, setLicencePlate] = useState("");
  const [targetCar, setTargetCar] = useState(null);
  const [errors, setErrors] = useState({});
  const [userId, setUserId] = useState(null);
  const endpoint = useEndpoint();

  const fetchCarByPlate = useCallback(async (userId, plate) => {
    const token = JSON.parse(localStorage.getItem("user"))?.token;
    if (!token) {
      console.error("Erro de autenticação. Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await axios.get(`${endpoint}/users/${userId}/cars`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const carsData = response.data;
      const foundCar = carsData.find((car) => car.licencePlate === plate);
      if (foundCar) {
        setTargetCar(foundCar);
        const carServicesResponse = await axios.get(
          `${endpoint}/users/${userId}/cars/${foundCar.idCar}/maintenance`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setCarServices(carServicesResponse.data);
        setSearched(true);
        setErrors({});
      } else {
        setTargetCar(null);
        setCarServices([]);
        setSearched(true);
        setErrors({ licencePlate: "Carro não encontrado" });
      }
    } catch (error) {
      console.error("Erro ao buscar informações do carro:", error);
      setErrors({ licencePlate: "Erro ao buscar informações do carro" });
    }
  }, [endpoint]);

  const validate = () => {
    const newErrors = {};
    const plateRegex = /^[A-Z]{3}-[A-Z0-9]{4}$/;

    if (!licencePlate) {
      newErrors.licencePlate = "Placa do carro é obrigatória.";
    } else if (!plateRegex.test(licencePlate)) {
      newErrors.licencePlate = "Formato de placa inválido. Use o formato AAA-AAAA.";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSearch = async () => {
    if (!validate()) return;
    await fetchCarByPlate(userId, licencePlate);
  };

  const handleTokenDecoded = useCallback((id) => {
    setUserId(id);
  }, []);

  const handleInputChange = (e) => {
    const { value } = e.target;
    const formattedValue = value
      .toUpperCase()
      .replace(/[^A-Z0-9]/g, "")
      .replace(/(.{3})(.)/, "$1-$2");
    setLicencePlate(formattedValue);
  };

  return (
    <div>
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <div className="search-container">
        <input
          type="text"
          name="licencePlate"
          placeholder="Digite a placa"
          value={licencePlate}
          onChange={handleInputChange}
          className={`search-input ${errors.licencePlate ? "input-error" : ""}`}
          maxLength="8"
        />
        {errors.licencePlate && <p className="error-message">{errors.licencePlate}</p>}
        <button onClick={handleSearch} className="search-button">
          Pesquisar
        </button>
      </div>
      <div className="table-container">
        <div className="table-header">
          <div className="table-title">
            <h3>Tabela de Serviços</h3>
          </div>
        </div>
        {searched && targetCar && (
          <table className="car-table">
            <thead>
              <tr>
                <th>N. Serviço</th>
                <th>Proprietário</th>
                <th>Placa</th>
                <th>Marca</th>
                <th>Modelo</th>
                <th>Cor</th>
                <th>Chassi</th>
                <th>Preço</th>
                <th>Descrição do Serviço</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {carServices.map((maintenance) => (
                <tr key={`${targetCar.idCar}_${maintenance.idMaintenance}`}>
                  <td>{maintenance.idMaintenance}</td>
                  <td>{targetCar.ownerName}</td>
                  <td>{targetCar.licencePlate}</td>
                  <td>{targetCar.carBrand}</td>
                  <td>{targetCar.model}</td>
                  <td>{targetCar.color}</td>
                  <td>{targetCar.chassisNumber}</td>
                  <td>{maintenance.serviceValue}</td>
                  <td>{maintenance.serviceDescription}</td>
                  <td>{maintenance.serviceStatus}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default TableCar;
