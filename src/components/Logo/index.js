import React from "react";
import { Link } from "react-router-dom"; 
import "./styles.css";

function Logo(props) {
  const { titleColor, barColor } = props;

  return (
    <div className="logo-container">
      <Link to="/" className="logo-title" style={{ color: titleColor }}>
        AutoLog
      </Link>
      <div className="bar-title" style={{ backgroundColor: barColor }}></div>
    </div>
  );
}

export default Logo;
