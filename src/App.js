import React, { useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Historic from "./pages/Historic";
import ServicesRegister from "./pages/ServicesRegister";
import CarRegister from "./pages/CarRegister";
import ConfigUser from "./pages/ConfigUser";
import Config from "./pages/Config";
import Edit from "./pages/Edit";
import Car from "./pages/SearchCar";
import Teste from "./Test";
import LandingPage from "./pages/landingPage";
import PasswordSection from "./pages/PasswordSection";
import { AuthProvider, useAuth } from "./services/AuthContext";
import ProtectedRoute from "./routes/ProtectedRoutes";
import setupInterceptors from "./services/setupInterceptors";


const App = () => {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
};

const AppContent = () => {
  const { logout } = useAuth();

  useEffect(() => {
    setupInterceptors(logout);
  }, [logout]);

  return (
    <Router>
      <Routes>
        <Route exact path="/login" element={<Login />} />
        <Route exact path="/register" element={<Register />} />
        <Route exact path="/passwordRecovery" element={<PasswordSection />} />
        <Route exact path="/" element={<LandingPage />} />
        <Route element={<ProtectedRoute />}>
          <Route path="/carRegister" element={<CarRegister />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/servicesRegister" element={<ServicesRegister />} />
          <Route path="/historic" element={<Historic />} />
          <Route path="/configUser" element={<ConfigUser />} />
          <Route path="/config" element={<Config />} />
          <Route path="/editService" element={<Edit />} />
          <Route path="/searchCar" element={<Car />} />
          <Route path="/test" element={<Teste />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default App;
