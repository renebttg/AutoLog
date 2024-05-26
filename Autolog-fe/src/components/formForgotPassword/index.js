import React, { useState } from "react";
//import axios from "axios";
import Input from "../Inputs";
import Button from "../Buttons";
import Logo from "../Logo";
import { validateStrongPassword } from "../../utils/inputsFormat";

const ForgotPassword = () => {
  const [formData, setFormData] = useState({
    password: "",
    confirmPassword: ""
  });
  const [errors, setErrors] = useState({});
  const [showErrors, setShowErrors] = useState(false);
  const [message, /*setMessage*/] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });

    if (name === "password" && !validateStrongPassword(value)) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        password: "Senha fraca. A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial.",
      }));
    } else if (name === "password") {
      setErrors((prevErrors) => {
        const { password, ...rest } = prevErrors;
        return rest;
      });
    }
  };

  const validatePasswords = () => {
    let validationErrors = {};
    if (!formData.password) {
      validationErrors.password = "Senha é obrigatória.";
    } else if (!validateStrongPassword(formData.password)) {
      validationErrors.password = "Senha fraca. A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial.";
    }

    if (formData.password !== formData.confirmPassword) {
      validationErrors.confirmPassword = "As senhas não coincidem.";
    }
    return validationErrors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setShowErrors(true);

    const validationErrors = validatePasswords();
    setErrors(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
      try {
        // Chamada ao serviço de alteração de senha
        // const response = await axios.post('/api/change-password', formData);
        // if (response.data.success) {
        //   setMessage("Senha alterada com sucesso!");
        //   // Redirecionar ou realizar outra ação após sucesso
        // } else {
        //   setErrors({ form: "Erro ao alterar a senha. Tente novamente." });
        // }
      } catch (error) {
        setErrors({ form: "Erro ao alterar a senha. Tente novamente." });
      }
    }
  };

  return (
    <div className="formRegister-container">
      <div className="logo-section">
        <Logo titleColor="#0A0E14" barColor="#B4C3CC" />
      </div>
      <form className="form-register" onSubmit={handleSubmit}>
        <div className="title-form">
          <h1>Alterar Senha</h1>
          <p>Insira sua nova senha para acessar sua área de gerenciamento.</p>
        </div>
        <div className="form-inputs">
          <Input
            labelText="Digite sua nova senha"
            labelFor="password"
            inputType="password"
            name="password"
            placeholder="Digite sua senha"
            autoComplete="off"
            value={formData.password}
            onChange={handleInputChange}
            errorMessage={errors.password}
            showError={showErrors}
          />
          <Input
            labelText="Confirme sua nova senha"
            labelFor="confirmPassword"
            inputType="password"
            name="confirmPassword"
            placeholder="Confirme sua senha"
            autoComplete="off"
            value={formData.confirmPassword}
            onChange={handleInputChange}
            errorMessage={errors.confirmPassword}
            showError={showErrors}
          />
        </div>
        {errors.form && <p className="form-error-message">{errors.form}</p>}
        {message && <p className="form-success-message">{message}</p>}
        <Button textBtn="Alterar Senha" type="submit" />
      </form>
      <div className="link-section">
        <div className="link-form">
          <p className="link-text">
            Ainda não possui uma conta?{" "}
            <a href="/register" className="link">
              Registre-se
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}

export default ForgotPassword;
