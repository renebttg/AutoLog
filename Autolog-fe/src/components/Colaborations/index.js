import React from "react";
import "./styles.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import collaborationImage from "../../assets/imgs/dashboard-AutologWb.png";

const CollaborationSection = () => {
  return (
    <section id="project" className="collaboration">
      <div className="container">
        <div className="image-content">
          <img src={collaborationImage} alt="AutoLog Dashboard" />
        </div>
        <div className="text-colab">
          <h2>
            Controle Total da Sua Oficina com AutoLog
          </h2>
          <p>
            Tenha todos os dados da sua oficina ao seu alcance. Com a dashboard completa do AutoLog, você gerencia registros de veículos, acompanha os serviços realizados e consulta o histórico de manutenção de forma intuitiva e eficiente. Nossa plataforma oferece uma visão detalhada e organizada, permitindo que você otimize a produtividade e a eficiência da sua oficina com facilidade.
          </p>
        </div>
      </div>
    </section>
  );
};

export default CollaborationSection;
