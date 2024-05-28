import React, { useState } from "react";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import "./styles.css";

function SearchBar({ onSearchResults }) {
  const [query, setQuery] = useState("");
  const navigate = useNavigate();

  const handleSearch = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/users/1/cars`);
      const carsData = response.data;
      const foundCar = carsData.find((car) => car.licencePlate === query);
      if (foundCar) {
        navigate(`/searchCar/${foundCar.idCar}`);
        onSearchResults(foundCar);
      } else {
        console.error("Carro não encontrado");
      }
    } catch (error) {
      console.error("Erro ao buscar informações do carro:", error);
    }
  };

  const handleKeyPress = (event) => {
    if (event.key === "Enter") {
      handleSearch();
    }
  };

  return (
    <div className="search-bar-container">
      <input
        type="text"
        placeholder="Busque um veículo pelo Placa"
        className="search-bar-input"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        onKeyPress={handleKeyPress}
      />
      <button type="submit" className="search-bar-btn" onClick={handleSearch}>
        <FontAwesomeIcon icon={faSearch} className="icon" />
      </button>
    </div>
  );
}

export default SearchBar;
