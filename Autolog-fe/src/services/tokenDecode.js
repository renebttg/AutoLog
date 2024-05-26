import { useEffect } from "react";
import { jwtDecode } from "jwt-decode";

function DecodificarToken({ onTokenDecoded }) {
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const token = user?.token;

    if (token) {
      const decodificado = jwtDecode(token);
      onTokenDecoded(decodificado.id);
    }
  }, [onTokenDecoded]);

  return null;
}

export default DecodificarToken;
