import React from 'react';
import './styles.css';

const Button = ({ textBtn, onClick }) => {
  return (
    <div className="btn-submit">
      <button type="submit" onClick={onClick}>{textBtn}</button>
    </div>
  );
};

export default Button;
