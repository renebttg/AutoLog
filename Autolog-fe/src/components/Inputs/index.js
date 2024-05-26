import React, { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faTimes, faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import { formatLicencePlate, formatChassisNumber, formatCNPJ, formatPhone } from "../../utils/inputsFormat"; 
import "./styles.css";

function Input({ labelText, labelFor, onChange, inputType, name, placeholder, autoComplete, errorMessage, value, showError }) {
  const [localError, setLocalError] = useState("");
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);

  const handleInputChange = (e) => {
    const inputValue = e.target.value;
    const formattedValue = formatValue(inputValue);
    onChange({ target: { name, value: formattedValue } });
  };

  const formatValue = (value) => {
    let formattedValue = value;
    if (name === "licencePlate") {
      formattedValue = formatLicencePlate(formattedValue);
    } else if (name === "chassisNumber") {
      formattedValue = formatChassisNumber(formattedValue);
    } else if (name === "cnpj") {
      formattedValue = formatCNPJ(formattedValue);
    } else if (name === "phone") {
      formattedValue = formatPhone(formattedValue);
    }
    return formattedValue;
  };

  const togglePasswordVisibility = () => {
    setIsPasswordVisible(!isPasswordVisible);
  };

  useEffect(() => {
    if (name === "licencePlate" && value.length < 7) {
      setLocalError("Placa incompleta.");
    } else if (name === "chassisNumber" && value.length < 17) {
      setLocalError("Número do chassi incompleto.");
    } else if (name === "cnpj" && value.length < 18) {
      setLocalError("CNPJ incompleto.");
    } else if (name === "phone" && value.length < 15) {
      setLocalError("Telefone incompleto.");
    } else {
      setLocalError("");
    }
  }, [name, value]);

  return (
    <div className="input-container">
      <label htmlFor={labelFor}>{labelText}</label>
      <div className="input-wrapper">
        <input
          className={`input-field ${showError && (errorMessage || localError) ? 'input-error' : ''}`}
          onChange={handleInputChange}
          type={(name === "password" || name === "confirmPassword") ? (isPasswordVisible ? "text" : "password") : inputType}
          name={name}
          placeholder={placeholder}
          value={value}
          autoComplete={autoComplete}
          required
        />
        {(name === "password" || name === "confirmPassword") && (
          <FontAwesomeIcon
            icon={isPasswordVisible ? faEyeSlash : faEye}
            className="password-icon"
            onClick={togglePasswordVisibility}
          />
        )}
        {value && (
          <div className="icon-container">
            {showError && (errorMessage || localError) ? (
              <FontAwesomeIcon icon={faTimes} className="error-icon" />
            ) : (
              <FontAwesomeIcon icon={faCheck} className="check-icon" />
            )}
          </div>
        )}
      </div>
      {showError && (errorMessage || localError) && <span className="error-message">{errorMessage || localError}</span>}
    </div>
  );
}

export default Input;
