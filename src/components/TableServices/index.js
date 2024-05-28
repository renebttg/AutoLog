import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import DecodificarToken from "../../services/tokenDecode";
import "./styles.css";

function TableServices() {
  const [cars, setCars] = useState([]);
  const [showAll, setShowAll] = useState(false);
  const navigate = useNavigate();

  const fetchData = async (userId) => {
    try {
      const response = await axios.get(
        `https://autolog-deploy.azurewebsites.net/users/${userId}/cars`
      );
      const sortedData = response.data.map((car) => {
        const sortedMaintenanceHistory = car.maintenanceHistory.sort(
          (a, b) => a.idMaintenance - b.idMaintenance
        );
        return { ...car, maintenanceHistory: sortedMaintenanceHistory };
      });
      setCars(sortedData);
    } catch (error) {
      console.error("Erro ao buscar dados:", error);
    }
  };

  const handleTokenDecoded = (id) => {
    fetchData(id);
  };

  const handleRedirect = () => {
    navigate("/carRegister");
  };

  const handleEdit = () => {
    navigate("/editService");
  };

  const toggleShowAll = () => {
    setShowAll(!showAll);
  };

  const currentDate = new Date().toISOString().split("T")[0];

  return (
    <div className="table-container">
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <div className="table-header">
        <div className="table-title">
          <h3>Tabela de Serviços</h3>
        </div>
        <div className="table-btns">
          <button className="edit-btn" onClick={handleEdit}>
            Editar
          </button>
          <button className="add-btn" onClick={handleRedirect}>
            Adicionar
          </button>
          <button className="filter-btn" onClick={toggleShowAll}>
            {showAll ? "Mostrar Serviços do Dia" : "Mostrar Todos os Serviços"}
          </button>
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
            <th>Data</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {cars.map((car) =>
            car.maintenanceHistory
              .filter(
                (maintenance) => maintenance.serviceStatus !== "Concluído"
              )
              .filter(
                (maintenance) =>
                  showAll || maintenance.repairDate === currentDate
              )
              .map((maintenance) => (
                <tr key={`${car.idCar}_${maintenance.idMaintenance}`}>
                  <td>{maintenance.idMaintenance}</td>
                  <td>{car.ownerName}</td>
                  <td>{car.licencePlate}</td>
                  <td>{car.carBrand}</td>
                  <td>{car.model}</td>
                  <td>{car.color}</td>
                  <td>{car.chassisNumber}</td>
                  <td>{maintenance.serviceValue}</td>
                  <td>{maintenance.serviceDescription}</td>
                  <td>{maintenance.repairDate}</td>
                  <td>{maintenance.serviceStatus}</td>
                </tr>
              ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default TableServices;
