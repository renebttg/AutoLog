import React from 'react';
import './styles.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import aboutImage from '../../assets/imgs/MechanicWorkshop.png.jpg';

const About = () => {
  return (
    <section id="about" className="about">
      <div className="container">
        <div className="about-grid">
          <div className="about-text">
            <h2>Sobre o AutoLog</h2>
            <p>O AutoLog é uma plataforma inovadora que ajuda você a gerenciar a manutenção e serviços dos seus veículos com facilidade e eficiência. Nosso sistema permite que você acompanhe todas as informações importantes em um único lugar.</p>
          </div>
          <div className="about-image">
            <img src={aboutImage} alt="AutoLog Platform" />
          </div>
        </div>
        <div className="about-stats">
          <div className="stat-item">
            <h3>2.5</h3>
            <p>Anos de Experiência</p>
          </div>
          <div className="stat-item">
            <h3>95%</h3>
            <p>Feedback Positivo</p>
          </div>
          <div className="stat-item">
            <h3>300+</h3>
            <p>Oficinas Registradas</p>
          </div>
          <div className="stat-item">
            <h3>100K</h3>
            <p>Ordens de Serviço Processadas</p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default About;
