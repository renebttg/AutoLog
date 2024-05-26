import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Input from "../Inputs";
import Button from "../Buttons";
import Logo from "../Logo";
import { validateEmail, validatePassword } from "../../utils/inputsFormat";
import UserServices from "../../services/userService";
import { useAuth } from "../../services/AuthContext";

function FormLogin() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });
  const [errors, setErrors] = useState({});
  const [showErrors, setShowErrors] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setLoginData({
      ...loginData,
      [name]: value,
    });

    if (name === "email") {
      if (!validateEmail(value)) {
        setErrors((prevErrors) => ({
          ...prevErrors,
          email: "Email inválido.",
        }));
      } else {
        setErrors((prevErrors) => {
          const { email, ...rest } = prevErrors;
          return rest;
        });
      }
    }

    if (name === "password") {
      if (!validatePassword(value)) {
        setErrors((prevErrors) => ({
          ...prevErrors,
          password: "Senha inválida.",
        }));
      } else {
        setErrors((prevErrors) => {
          const { password, ...rest } = prevErrors;
          return rest;
        });
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setShowErrors(true);

    let validationErrors = {};
    if (!validateEmail(loginData.email)) {
      validationErrors.email = "Email inválido.";
    }
    if (!validatePassword(loginData.password)) {
      validationErrors.password = "Senha inválida.";
    }

    setErrors(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
      const userService = new UserServices();
      const loginSuccess = await userService.login(loginData);

      if (loginSuccess) {
        const user = JSON.parse(localStorage.getItem("user"));
        if (user && user.token) {
          login(user.token); 
          navigate("/dashboard");
        }
      } else {
        setErrors({ form: "Erro ao fazer login. Verifique suas credenciais." });
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
          <h1>Login</h1>
          <p>Insira seus dados para acessar sua área de gerenciamento.</p>
        </div>
        <div className="form-inputs">
          <Input
            labelText="Email"
            labelFor="email"
            inputType="email"
            name="email"
            placeholder="Digite seu email"
            autoComplete="off"
            value={loginData.email}
            onChange={handleInputChange}
            errorMessage={errors.email}
            showError={showErrors}
          />
          <Input
            labelText="Senha"
            labelFor="password"
            inputType="password"
            name="password"
            placeholder="Digite sua senha"
            autoComplete="off"
            value={loginData.password}
            onChange={handleInputChange}
            errorMessage={errors.password}
            showError={showErrors}
          />
        </div>
        {errors.form && <p className="form-error-message">{errors.form}</p>}
        <Button textBtn="Entrar" type="submit" />
        <div className="forgotPassword">
          <a href="/passwordRecovery" className="link"> Esqueceu sua senha?</a>

        </div>
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

export default FormLogin;
