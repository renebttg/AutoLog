export const fetchUserData = async () => {
  const token = JSON.parse(localStorage.getItem("user"))?.token;
  if (!token) {
    throw new Error("Erro de autenticação. Por favor, faça login novamente.");
  }

  try {
    const response = await fetch("SEU_ENDPOINT_DE_USUARIO", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error("Erro ao buscar os dados do usuário.");
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Erro ao buscar os dados do usuário:", error);
    throw error;
  }
};
