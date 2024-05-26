import React from "react";
import "../styles/landingPage.css";
import Hero from "../components/Hero";
import Features from "../components/Features";
import About from "../components/About";
import Footer from "../components/Footer";
import CollaborationSection from "../components/Colaborations";
import TrialSection from "../components/Trial";

function LandingPage() {
  return (
    <div className="container-landing">
      <Hero />
      <Features />
      <CollaborationSection />
      <About />
      <TrialSection />
      <Footer />
    </div>
  );
}

export default LandingPage;
