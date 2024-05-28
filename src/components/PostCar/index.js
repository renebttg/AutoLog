import React, { useState } from "react";
import Input from "../Inputs";
import Select from "../Inputs/Select";
import DecodificarToken from "../../services/tokenDecode";
import "./styles.css";

function CarRegistrationForm() {
  const [formData, setFormData] = useState({
    ownerName: "",
    carBrand: "",
    model: "",
    color: "",
    licencePlate: "",
    chassisNumber: "",
  });

  const [errors, setErrors] = useState({});
  const [message, setMessage] = useState("");
  const [userId, setUserId] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.ownerName)
      newErrors.ownerName = "Nome do proprietário é obrigatório.";
    if (!formData.carBrand)
      newErrors.carBrand = "Marca do carro é obrigatória.";
    if (!formData.model) newErrors.model = "Modelo do carro é obrigatório.";
    if (!formData.color) newErrors.color = "Cor do carro é obrigatória.";
    if (!formData.licencePlate)
      newErrors.licencePlate = "Placa do carro é obrigatória.";
    if (!formData.chassisNumber)
      newErrors.chassisNumber = "Número do chassi é obrigatório.";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) {
      setMessage("Por favor, corrija os erros no formulário.");
      return;
    }

    const token = JSON.parse(localStorage.getItem("user"))?.token;
    if (!token) {
      setMessage("Erro de autenticação. Por favor, faça login novamente.");
      return;
    }

    try {
      const response = await fetch(
        `https://autolog-deploy.azurewebsites.net/users/${userId}/cars`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(formData),
        }
      );
      if (response.ok) {
        setFormData({
          ownerName: "",
          carBrand: "",
          model: "",
          color: "",
          licencePlate: "",
          chassisNumber: "",
        });
        setMessage("Veículo registrado com sucesso!");
      } else {
        setMessage("Erro ao registrar veículo.");
      }
    } catch (error) {
      console.error("Erro ao adicionar veículo:", error.message);
      setMessage("Erro ao registrar veículo.");
    }

    setTimeout(() => {
      window.location.reload();
    }, 2000);
  };

  const handleTokenDecoded = (id) => {
    setUserId(id);
  };

  const carBrands = [
    "Audi",
    "BMW",
    "Chevrolet",
    "Citroën",
    "Fiat",
    "Ford",
    "Honda",
    "Hyundai",
    "Jeep",
    "Kia",
    "Land Rover",
    "Mercedes-Benz",
    "Mitsubishi",
    "Nissan",
    "Peugeot",
    "Renault",
    "Suzuki",
    "Toyota",
    "Volkswagen",
    "Volvo",
  ];

  const carColors = [
    "Amarelo",
    "Azul",
    "Branco",
    "Cinza",
    "Laranja",
    "Marrom",
    "Preto",
    "Prata",
    "Verde",
    "Vermelho",
  ];

  return (
    <div className="car-registration-form">
      <DecodificarToken onTokenDecoded={handleTokenDecoded} />
      <form onSubmit={handleSubmit} className="form">
        <h1 className="form-title">Registro de Veículo</h1>
        <Input
          labelText="Nome do Proprietário"
          labelFor="ownerName"
          inputType="text"
          onChange={handleInputChange}
          value={formData.ownerName}
          name="ownerName"
          placeholder="Digite o nome do proprietário"
          autoComplete="on"
          errorMessage={errors.ownerName}
        />
        <Select
          labelText="Marca do Carro"
          labelFor="carBrand"
          onChange={handleInputChange}
          value={formData.carBrand}
          name="carBrand"
          options={carBrands.map((brand) => ({ value: brand, label: brand }))}
          placeholder="Selecione a marca do carro"
          errorMessage={errors.carBrand}
        />
        <Input
          labelText="Modelo"
          labelFor="model"
          inputType="text"
          onChange={handleInputChange}
          value={formData.model}
          name="model"
          placeholder="Digite o modelo do carro"
          autoComplete="on"
          errorMessage={errors.model}
        />
        <Select
          labelText="Cor"
          labelFor="color"
          onChange={handleInputChange}
          value={formData.color}
          name="color"
          options={carColors.map((color) => ({ value: color, label: color }))}
          placeholder="Selecione a cor do carro"
          errorMessage={errors.color}
        />
        <Input
          labelText="Placa do Carro"
          labelFor="licencePlate"
          inputType="text"
          onChange={handleInputChange}
          value={formData.licencePlate}
          name="licencePlate"
          placeholder="Digite a placa do carro"
          errorMessage={errors.licencePlate}
        />
        <Input
          labelText="Número do Chassi"
          labelFor="chassisNumber"
          inputType="text"
          onChange={handleInputChange}
          value={formData.chassisNumber}
          name="chassisNumber"
          placeholder="Digite o número do chassi"
          errorMessage={errors.chassisNumber}
        />
        <div className="btn-container">
          <button type="submit" className="submit-btn">
            Registrar Veículo
          </button>
        </div>
        {message && (
          <p
            className={`form-message ${
              message === "Veículo registrado com sucesso!"
                ? "success"
                : "error"
            }`}
          >
            {message}
          </p>
        )}
      </form>
    </div>
  );
}

export default CarRegistrationForm;
