import React from 'react';
import './styles.css';
import '@fortawesome/fontawesome-free/css/all.min.css';


const Features = () => {
  return (
    <section id="features" className="features">
      <div className="container">
        <h2>Recursos Exclusivos</h2>
        <div className="feature-list">
          <div className="feature-item">
            <i className="fas fa-car"></i>
            <h3>Gestão de Veículos</h3>
            <p>Gerencie todas as informações dos seus veículos em um único lugar.</p>
          </div>
          <div className="feature-item">
            <i className="fas fa-tools"></i>
            <h3>Serviços e Manutenção</h3>
            <p>Acompanhe e registre todas as manutenções e serviços realizados.</p>
          </div>
          <div className="feature-item">
            <i className="fas fa-file-alt"></i>
            <h3>Relatórios Detalhados</h3>
            <p>Gere relatórios detalhados sobre o histórico de manutenção dos seus veículos.</p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Features;
