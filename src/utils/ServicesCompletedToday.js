import React, { useState, useCallback, useEffect } from "react";
import axios from "axios";
import DecodificarToken from "../services/tokenDecode";
import { useEndpoint } from "../services/EndpointContext";

function ServicesCompletedToday() {
  const [completedServicesCount, setCompletedServicesCount] = useState(0);
  const [userId, setUserId] = useState(null);
  const endpoint = useEndpoint();

  const fetchCompletedServicesCount = useCallback(
    async (userId) => {
      try {
        const response = await axios.get(`${endpoint}/users/${userId}/cars`);
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
    },
    [endpoint]
  );

  const handleTokenDecoded = useCallback((id) => {
    setUserId(id);
  }, []);

  useEffect(() => {
    if (userId) {
      fetchCompletedServicesCount(userId);
    }
  }, [userId, fetchCompletedServicesCount]);

  return (
    <div>
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <span>{completedServicesCount}</span>
    </div>
  );
}

export default ServicesCompletedToday;
