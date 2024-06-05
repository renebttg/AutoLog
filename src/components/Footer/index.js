import React from "react";
import "./styles.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

const Footer = () => {
  return (
    <footer className="footer" id="contact">
      <div className="container">
        <div className="footer-columns">
          <div className="footer-column">
            <h3>Equipe AutoLog</h3>
            <ul>
              <li>
                <a
                  href="https://github.com/Ale-Sampaio"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Alexandre Sampaio - FrontEnd
                </a>
              </li>
              <li>
                <a
                  href="https://github.com/Jidsx"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Jean Israel - Diagramas
                </a>
              </li>
              <li>
                <a
                  href="https://github.com/Motielk"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Murilo Henrique - Scrum Master
                </a>
              </li>
              <li>
                <a
                  href="https://github.com/renebttg"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Rene Battaglia - BackEnd
                </a>
              </li>
              <li>
                <a
                  href="https://github.com/Testorugo"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Vitor Hugo - Documentação
                </a>
              </li>
            </ul>
          </div>
          <div className="footer-column">
            <h3>Suporte</h3>
            <ul>
              <li>
                <a href="/">Ajuda e FAQ</a>
              </li>
              <li>
                <a href="/">Contate-nos</a>
              </li>
              <li>
                <a href="/">Políticas de Suporte</a>
              </li>
            </ul>
          </div>
          <div className="footer-column">
            <h3>Contato</h3>
            <p>Email: suporte@autolog.com</p>
            <p>Telefone: (15) 1234-5678</p>
          </div>
          <div className="footer-column">
            <h3>Siga-nos</h3>
            <div className="social-icons">
              <a href="/">
                <i className="fab fa-facebook-f"></i>
              </a>
              <a href="/">
                <i className="fab fa-twitter"></i>
              </a>
              <a href="/">
                <i className="fab fa-linkedin-in"></i>
              </a>
              <a href="/">
                <i className="fab fa-instagram"></i>
              </a>
            </div>
          </div>
        </div>
        <div className="footer-bottom">
          <p>&copy; 2024 AutoLog. Todos os direitos reservados.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
