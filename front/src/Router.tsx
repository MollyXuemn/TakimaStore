import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import React from "react";
import HomePage from "./pages/HomePage/HomePage";
import ArticlePage from "./pages/ArticlePage/ArticlePage";
import SellerHomePage from "./pages/SellerHomePage/SellerHomePage";
import ProfilePage from "./pages/ProfilePage/ProfilePage";

export default function Router() {
  return (
    <React.StrictMode>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />}>
            {/*L'attribut index permet d'indiquer la route qui sera présente par défaut pour la route parente -->*/}
            <Route index element={<HomePage />} />
            <Route path="articles" element={<HomePage />} />

            {/*Cette route sera afficher à la place de la précédente si le path match l'url actuel*/}
            <Route path="/articles/:articleId" element={<ArticlePage />} />
            <Route path="sellers" element={<SellerHomePage />} />
            {/* eslint-disable-next-line react/jsx-no-undef */}
            <Route path="cart" element={<SellerHomePage />} />
            {/* eslint-disable-next-line react/jsx-no-undef */}
            <Route path="/customers/${userId}" element={<ProfilePage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </React.StrictMode>
  );
}
