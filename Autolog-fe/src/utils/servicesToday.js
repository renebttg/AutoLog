import React, { useState } from "react";
import axios from "axios";
import DecodificarToken from "../services/tokenDecode";

function ServicesToday() {
  const [servicesCount, setServicesCount] = useState(0);

  const fetchServicesCount = async (userId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/users/${userId}/cars`
      );
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
  };

  const handleTokenDecoded = (id) => {
    fetchServicesCount(id);
  };

  return (
    <div>
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <h1>{servicesCount}</h1>
    </div>
  );
}

export default ServicesToday;
