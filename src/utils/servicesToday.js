import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import DecodificarToken from "../services/tokenDecode";
import { useEndpoint } from "../services/EndpointContext";

function ServicesToday() {
  const [servicesCount, setServicesCount] = useState(0);
  const [userId, setUserId] = useState(null);
  const endpoint = useEndpoint();

  const fetchServicesCount = useCallback(
    async (userId) => {
      try {
        const response = await axios.get(`${endpoint}/users/${userId}/cars`);
        const cars = response.data;

        const currentDate = new Date().toISOString().split("T")[0];

        let count = 0;
        cars.forEach((car) => {
          car.maintenanceHistory.forEach((service) => {
            if (service.repairDate === currentDate) {
              count++;
            }
          });
        });

        setServicesCount(count);
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
      fetchServicesCount(userId);
    }
  }, [userId, fetchServicesCount]);

  return (
    <div>
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <span>{servicesCount}</span>
    </div>
  );
}

export default ServicesToday;
