import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './styles.css';
import landingImage from '../../assets/imgs/CarLandingPage.png';
import HeaderLP from '../HeaderLP';

const Hero = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const header = document.querySelector('.header');
    const hero = document.querySelector('.hero');

    const handleScroll = () => {
      const heroHeight = hero.offsetHeight;
      if (window.scrollY > heroHeight) {
        header.classList.add('scrolled');
      } else {
        header.classList.remove('scrolled');
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  const handleStartNow = () => {
    navigate('/register');
  };

  return (
    <section className="hero" id="home" style={{ backgroundImage: `url(${landingImage})` }}>
      <HeaderLP />
      <div className="container">
        <div className="hero-content">
          <h1>Gerencie sua Oficina com Agilidade e Eficiência usando <strong>AutoLog!</strong></h1>
          <p>Uma solução completa para o gerenciamento de manutenção e serviços automotivos.</p>
          <button className="btn" onClick={handleStartNow}>Comece agora</button>
        </div>
      </div>
    </section>
  );
}

export default Hero;
