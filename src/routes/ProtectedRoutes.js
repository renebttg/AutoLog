import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../services/AuthContext";

const ProtectedRoute = () => {
  const { authToken } = useAuth();

  return authToken ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoute;
