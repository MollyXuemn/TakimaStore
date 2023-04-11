import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./App";
import React from "react";
import HomePage from "./pages/HomePage/HomePage";
import ArticlePage from "./pages/ArticlePage/ArticlePage";

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
            {/*<Route path="sellers" element={<SellerHomePage />} />
            <Route path="cart" element={<CartPage />} />
            <Route path="profile" element={<ProfilePage />} />*/}
          </Route>
        </Routes>
      </BrowserRouter>
    </React.StrictMode>
  );
}
