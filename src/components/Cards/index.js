import React from "react";
import './styles.css';

function Cards({ title, value, height }) {
    return (
        <div className="dashboard-card" style={{ height: height }}>
            <p>{title}</p>
            <h1>{value}</h1>
        </div>
    );
}

export default Cards;
