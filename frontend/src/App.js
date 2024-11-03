import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Features from './pages/Features';
import Location from './pages/Location';
import Restaurants from './pages/Restaurants';
import Bars from './pages/Bars';
import Attractions from './pages/Attractions';
import Sights from './pages/Sights';
import './stylees.css';
const App = () => {
  return (
    <Routes>
      <Route path="" element={<Home />} />
      <Route path="/features" element={<Features />} />
      <Route path="/location" element={<Location />} />
      <Route path="/restaurants" element={<Restaurants />} />
      <Route path="/bars" element={<Bars />} />
      <Route path="/attractions" element={<Attractions />} />
      <Route path="/sights" element={<Sights />} />
    </Routes>
  );
};

export default App;
