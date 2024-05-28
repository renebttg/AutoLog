import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./styles.css";
import Input from "../Inputs";
import Button from "../Buttons";
import Logo from "../Logo";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import {
  validateEmail,
  validatePhone,
  validateStrongPassword,
} from "../../utils/inputsFormat";

function FormRegister() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    cnpj: "",
    nameWorkshop: "",
    addressWorkshop: "",
    phone: "",
  });

  const [step, setStep] = useState(1);
  const [errors, setErrors] = useState({});
  const [showErrors, setShowErrors] = useState(false);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });

    if (name === "email" && !validateEmail(value)) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        email: "Email inválido.",
      }));
    } else if (name === "email") {
      setErrors((prevErrors) => {
        const { email, ...rest } = prevErrors;
        return rest;
      });
    }

    if (name === "password" && !validateStrongPassword(value)) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        password:
          "Senha fraca. A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial.",
      }));
    } else if (name === "password") {
      setErrors((prevErrors) => {
        const { password, ...rest } = prevErrors;
        return rest;
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setShowErrors(true);

    let validationErrors = {};

    if (step === 1) {
      if (!formData.name) validationErrors.name = "Nome é obrigatório.";
      if (!validateEmail(formData.email)) {
        validationErrors.email = "Email inválido.";
      }
      if (!validateStrongPassword(formData.password)) {
        validationErrors.password =
          "Senha fraca. A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial.";
      }
      if (formData.password !== formData.confirmPassword) {
        validationErrors.confirmPassword = "As senhas não coincidem.";
      }

      setErrors(validationErrors);

      if (Object.keys(validationErrors).length === 0) {
        setStep(2);
        setShowErrors(false);
      }
    } else {
      if (formData.cnpj.length !== 18) {
        validationErrors.cnpj = "CNPJ inválido.";
      }
      if (!validatePhone(formData.phone)) {
        validationErrors.phone = "Telefone inválido.";
      }

      setErrors(validationErrors);

      if (Object.keys(validationErrors).length === 0) {
        const { confirmPassword, ...dataToSend } = formData;

        try {
          const response = await axios.post(
            "https://autolog-deploy.azurewebsites.net/auth/register",
            dataToSend,
            {
              headers: {
                "Content-Type": "application/json",
              },
            }
          );

          if (response.status === 201) {
            setMessage("Registro realizado com sucesso!");
            setTimeout(() => {
              navigate("/login");
            }, 2000);
          } else {
            setMessage("Erro ao realizar o registro. Tente novamente.");
          }
        } catch (error) {
          console.error(
            "Erro ao realizar o registro:",
            error.response || error
          );
          setMessage("Erro ao realizar o registro. Tente novamente.");
        }
      }
    }
  };

  const handleBackToStepOne = () => {
    setStep(1);
    setErrors({});
    setShowErrors(false);
  };

  return (
    <div className="formRegister-container">
      <Logo titleColor="#0A0E14" barColor="#B4C3CC" />
      <form className="form-register" onSubmit={handleSubmit}>
        <div className="title-form">
          <h1>{step === 1 ? "Crie sua conta" : "Dados da oficina"}</h1>
          <p className="step-info">
            {step === 1
              ? "Primeiro, preencha o formulário com suas informações básicas."
              : "Agora, preencha as informações da sua empresa para completar seu registro."}
          </p>
        </div>
        <div className="form-inputs">
          {step === 1 ? (
            <>
              <Input
                labelText="Nome"
                labelFor="name"
                inputType="text"
                name="name"
                placeholder="Digite seu nome"
                value={formData.name}
                onChange={handleInputChange}
                autoComplete="off"
                errorMessage={errors.name}
                showError={showErrors}
                required
              />
              <Input
                labelText="Email"
                labelFor="email"
                inputType="email"
                name="email"
                placeholder="Digite seu email"
                value={formData.email}
                onChange={handleInputChange}
                autoComplete="off"
                errorMessage={errors.email}
                showError={showErrors}
                required
              />
              <Input
                labelText="Senha"
                labelFor="password"
                inputType="password"
                name="password"
                placeholder="Digite sua senha"
                value={formData.password}
                onChange={handleInputChange}
                autoComplete="off"
                errorMessage={errors.password}
                showError={showErrors}
                required
              />
              <Input
                labelText="Confirme a Senha"
                labelFor="confirmPassword"
                inputType="password"
                name="confirmPassword"
                placeholder="Digite sua senha novamente"
                value={formData.confirmPassword}
                onChange={handleInputChange}
                autoComplete="off"
                errorMessage={errors.confirmPassword}
                showError={showErrors}
                required
              />
            </>
          ) : (
            <>
              <Input
                labelText="CNPJ (00.000.000/0000-00)"
                labelFor="cnpj"
                inputType="text"
                name="cnpj"
                placeholder="Digite o CNPJ da empresa"
                value={formData.cnpj}
                onChange={handleInputChange}
                autoComplete="off"
                errorMessage={errors.cnpj}
                showError={showErrors}
                required
              />
              <Input
                labelText="Nome da Empresa"
                labelFor="nameWorkshop"
                inputType="text"
                name="nameWorkshop"
                placeholder="Digite o nome da empresa"
                value={formData.nameWorkshop}
                onChange={handleInputChange}
                autoComplete="off"
                errorMessage={errors.nameWorkshop}
                showError={showErrors}
                required
              />
              <Input
                labelText="Endereço"
                labelFor="addressWorkshop"
                inputType="text"
                name="addressWorkshop"
                placeholder="Digite o endereço da empresa"
                value={formData.addressWorkshop}
                onChange={handleInputChange}
                autoComplete="off"
                errorMessage={errors.addressWorkshop}
                showError={showErrors}
                required
              />
              <Input
                labelText="Telefone (00) 00000-0000"
                labelFor="phone"
                inputType="tel"
                name="phone"
                placeholder="Digite o telefone da empresa"
                value={formData.phone}
                onChange={handleInputChange}
                autoComplete="off"
                errorMessage={errors.phone}
                showError={showErrors}
                required
              />
            </>
          )}
        </div>
        <Button
          textBtn={step === 1 ? "Próxima Etapa" : "Cadastrar"}
          type="submit"
        />
      </form>
      <div className="link-section">
        <div className="link-form">
          <p className="link-text">
            {step === 1 ? (
              <>
                Já possui uma conta?{" "}
                <a href="/login" className="link">
                  Login
                </a>
              </>
            ) : (
              <span className="link" onClick={handleBackToStepOne}>
                <FontAwesomeIcon icon={faArrowLeft} /> Voltar
              </span>
            )}
          </p>
        </div>
        {step === 2 && (
          <div className="terms-agreement">
            <p>
              Ao clicar, você está concordando com os nossos{" "}
              <strong className="strong-terms">Termos de Serviço</strong>.
            </p>
          </div>
        )}
      </div>
      {message && <div className="message">{message}</div>}
    </div>
  );
}

export default FormRegister;
