import React from 'react';
import { useNavigate } from 'react-router-dom';
import './styles.css';

const TrialSection = () => {
  const navigate = useNavigate();

  const handleStartTrial = () => {
    navigate('/register');
  };

  return (
    <section id="trial" className="trial-section">
      <div className="container">
        <div className="text-content">
          <h2>Comece seu teste gratuito de 30 dias</h2>
          <p>
            Experimente todas as funcionalidades do AutoLog sem compromisso. Gerencie sua oficina com eficiência e veja os benefícios da nossa plataforma.
          </p>
        </div>
        <div className="cta-content">
          <button className="cta-button" onClick={handleStartTrial}>Iniciar Teste</button>
        </div>
      </div>
    </section>
  );
}

export default TrialSection;
