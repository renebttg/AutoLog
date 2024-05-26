import React, { useState } from "react";
import axios from "axios";
import DecodificarToken from "../services/tokenDecode";

function ServicesCompletedToday() {
  const [completedServicesCount, setCompletedServicesCount] = useState(0);

  const fetchCompletedServicesCount = async (userId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/users/${userId}/cars`
      );
      const cars = response.data;

      const currentDate = new Date();
      const currentDateString = currentDate.toISOString().slice(0, 10);

      const completedServicesToday = cars.reduce((acc, car) => {
        const completedServices = car.maintenanceHistory.filter(
          (maintenance) => {
            return (
              maintenance.serviceStatus === "Concluído" &&
              maintenance.repairDate === currentDateString
            );
          }
        );
        return acc + completedServices.length;
      }, 0);

      setCompletedServicesCount(completedServicesToday);
    } catch (error) {
      console.error("Erro ao buscar dados:", error);
    }
  };

  const handleTokenDecoded = (id) => {
    fetchCompletedServicesCount(id);
  };

  return (
    <div>
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <span>{completedServicesCount}</span>
    </div>
  );
}

export default ServicesCompletedToday;
