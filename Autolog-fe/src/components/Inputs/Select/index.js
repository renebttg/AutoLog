import React from "react";
import "./styles.css";

function Select({ labelText, labelFor, onChange, name, options, placeholder, errorMessage, value }) {
  const handleSelectChange = (e) => {
    const value = e.target.value;
    onChange({ target: { name, value } });
  };

  return (
    <div className="select-container">
      <label className="select-label" htmlFor={labelFor}>{labelText}</label>
      <div className="select-wrapper">
        <select
          className="select-field"
          onChange={handleSelectChange}
          name={name}
          value={value}
        >
          <option value="">{placeholder}</option>
          {options.map((option) => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
        {value && (
          <div className="select-check-icon">&#10003;</div>
        )}
      </div>
      {errorMessage && <span className="error-message">{errorMessage}</span>}
    </div>
  );
}

export default Select;
