import React, { useState } from "react";
import axios from "axios";
import DecodificarToken from "../../services/tokenDecode";
import { useEndpoint } from "../../services/EndpointContext";

function TableHistoric() {
  const [completedServices, setCompletedServices] = useState([]);
  const endpoint = useEndpoint(); 

  const fetchData = async (userId) => {
    try {
      const response = await axios.get(`${endpoint}/users/${userId}/cars`);
      const cars = response.data;
      const completedServices = [];
      cars.forEach((car) => {
        car.maintenanceHistory.forEach((maintenance) => {
          if (maintenance.serviceStatus === "Concluído") {
            completedServices.push({
              car,
              maintenance,
            });
          }
        });
      });
      setCompletedServices(completedServices);
    } catch (error) {
      console.error("Erro ao buscar dados:", error);
    }
  };

  const handleTokenDecoded = (id) => {
    fetchData(id);
  };

  return (
    <div className="table-container">
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <div className="table-header">
        <div className="table-title">
          <h3>Historico de Serviços</h3>
        </div>
      </div>
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
            <th>Data Conclusão</th>
          </tr>
        </thead>
        <tbody>
          {completedServices.map((entry, index) => (
            <tr key={index}>
              <td>{entry.maintenance.idMaintenance}</td>
              <td>{entry.car.ownerName}</td>
              <td>{entry.car.licencePlate}</td>
              <td>{entry.car.carBrand}</td>
              <td>{entry.car.model}</td>
              <td>{entry.car.color}</td>
              <td>{entry.car.chassisNumber}</td>
              <td>{entry.maintenance.serviceValue}</td>
              <td>{entry.maintenance.serviceDescription}</td>
              <td>{entry.maintenance.serviceStatus}</td>
              <td>{entry.maintenance.repairDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TableHistoric;
