import React from "react";
import axios from "axios";

function TestConnection() {
  const testConnection = async () => {
    try {
      const response = await axios.get("http://localhost:8080/test");
      alert("Server response: " + response.data);
    } catch (error) {
      console.error("Error connecting to server:", error);
      alert("Failed to connect to server");
    }
  };

  return (
    <div>
      <button onClick={testConnection}>Test Connection</button>
    </div>
  );
}

export default TestConnection;
