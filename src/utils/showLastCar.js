import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import DecodificarToken from "../services/tokenDecode";
import { useEndpoint } from "../services/EndpointContext";

const fetchUserCars = async (userId, token, endpoint) => {
  try {
    const response = await axios.get(`${endpoint}/users/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    const userData = response.data;

    if (!userData.cars || userData.cars.length === 0) {
      return [];
    }

    return userData.cars;
  } catch (error) {
    console.error("lastPlateShowingError:", error);
    throw error;
  }
};

const ShowLastCar = () => {
  const [licencePlate, setLicencePlate] = useState(null);
  const [error, setError] = useState(null);
  const [userId, setUserId] = useState(null);
  const endpoint = useEndpoint();

  const handleTokenDecoded = useCallback((id) => {
    setUserId(id);
  }, []);

  useEffect(() => {
    const token = JSON.parse(localStorage.getItem("user"))?.token;
    if (!token) {
      console.error("Erro de autenticação. Por favor, faça login novamente.");
      return;
    }

    const getUserCarWithHighestId = async () => {
      try {
        const userCars = await fetchUserCars(userId, token, endpoint);

        if (userCars.length === 0) {
          setLicencePlate("-");
          return;
        }

        const carWithHighestId = userCars.reduce((maxCar, currentCar) => {
          return currentCar.idCar > maxCar.idCar ? currentCar : maxCar;
        }, userCars[0]);

        console.log(
          "Licence Plate of car with highest ID:",
          carWithHighestId.licencePlate
        );
        setLicencePlate(carWithHighestId.licencePlate);
      } catch (err) {
        setError(err.message);
      }
    };

    if (userId) {
      getUserCarWithHighestId();
    }
  }, [userId, endpoint]);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      {licencePlate ? <span>{licencePlate}</span> : <span>-</span>}
    </div>
  );
};

export default ShowLastCar;
