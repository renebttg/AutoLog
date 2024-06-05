import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import DecodificarToken from "../services/tokenDecode";
import { useEndpoint } from "../services/EndpointContext";

function MonthlyServiceValue() {
  const [totalValue, setTotalValue] = useState(0);
  const [userId, setUserId] = useState(null);
  const endpoint = useEndpoint();

  const fetchMonthlyServiceValue = useCallback(
    async (userId) => {
      try {
        const response = await axios.get(`${endpoint}/users/${userId}/cars`);
        const cars = response.data;

        const currentDate = new Date();
        const currentMonth = currentDate.getMonth() + 1;
        const currentYear = currentDate.getFullYear();

        const total = cars.reduce((acc, car) => {
          const monthlyServices = car.maintenanceHistory.filter(
            (maintenance) => {
              const repairDate = new Date(maintenance.repairDate);
              const repairMonth = repairDate.getMonth() + 1;
              const repairYear = repairDate.getFullYear();
              return repairMonth === currentMonth && repairYear === currentYear;
            }
          );
          const monthlyTotal = monthlyServices.reduce(
            (sum, service) => sum + parseFloat(service.serviceValue),
            0
          );
          return acc + monthlyTotal;
        }, 0);

        setTotalValue(total);
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    },
    [endpoint]
  );

  console.log(totalValue)

  useEffect(() => {
    if (userId) {
      fetchMonthlyServiceValue(userId);
    }
  }, [userId, fetchMonthlyServiceValue]);

  const handleTokenDecoded = useCallback((id) => {
    setUserId(id);
  }, []);

  return (
    <div>
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <span>R$ {totalValue.toFixed(2)}</span>
    </div>
  );
}

export default MonthlyServiceValue;
