import React from "react";
import "./styles.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function Cards({
  title,
  value,
  icon,
  buttonLabel,
  onButtonClick,
  subText,
  blackCard,
}) {
  return (
    <div className={`dashboard-card ${blackCard ? "black-card" : ""}`}>
      <div className="card-content">
        <div className="left-section">
          <p className="card-title">{title}</p>
          <div className="value-icon">
            <h1 className="card-value">{value}</h1>
            <FontAwesomeIcon icon={icon} className="card-icon" />
          </div>
          {subText && <p className="card-subtext">{subText}</p>}
        </div>
      </div>
      {buttonLabel && onButtonClick && (
        <button className="card-button" onClick={onButtonClick}>
          {buttonLabel}
        </button>
      )}
    </div>
  );
}

export default Cards;
