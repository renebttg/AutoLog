import React from "react";
import "./styles.css";

import oleo from '../../assets/imgs/man-working.jpg';

function MainCard() {
  return (
    <div className="mainCard-container">
        <div className="mc-text">
            <h1>
                Gerencie sua oficina
            </h1>
            <p>obtenha 20</p>
        </div>
        <div className="mc-img">
        <img src={oleo} alt="Descrição da imagem"  className="img-card"/>

        </div>
      
    </div>
  );
}

export default MainCard;
