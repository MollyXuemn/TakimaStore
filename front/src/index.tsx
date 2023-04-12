import ReactDOM from "react-dom/client";
import "./index.scss";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import React from "react";
import HomePage from "./pages/HomePage/HomePage";
import ArticlePage from "./pages/ArticlePage/ArticlePage";
import CartPage from "./pages/CartPage/CartPage";
import SellerHomePage from "./pages/SellerHomePage/SellerHomePage";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

root.render(
  <>
    {/*    <Router />*/}
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />}>
          <Route index element={<HomePage />} />
          <Route path="articles" element={<HomePage />} />
          <Route path="/articles/:articleId" element={<ArticlePage />} />
          <Route path="sellers" element={<SellerHomePage />} />
          <Route path="cart" element={<CartPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </>
);
// function Router() {
// //   return (
// //     <React.StrictMode>
// //       {/* eslint-disable-next-line react/jsx-no-undef */}
// //       <BrowserRouter>
// //         {/* eslint-disable-next-line react/jsx-no-undef */}
// //         <Routes>
// //           {/* eslint-disable-next-line react/jsx-no-undef */}
// //           <Route path="/" element={<App />}>
// //             {/*L'attribut index permet d'indiquer la route qui sera présente par défaut pour la route parente -->*/}
// //             <Route index element={<HomePage />} />
// //             {/* eslint-disable-next-line react/jsx-no-undef */}
// //             <Route path="articles" element={<HomePage />} />
// //
// //             {/*Cette route sera afficher à la place de la précédente si le path match l'url actuel*/}
// //             {/*<Route path="/articles/:articleId" element={<SellerHomePage />} />*/}
// //             {/*<Route path="sellers" element={<SellerHomePage />} />*/}
// //             {/*<Route path="cart" element={<SellerHomePage />} />*/}
// //             {/*<Route path="profile" element={<ProfilePage />} />*/}
// //           </Route>
// //         </Routes>
// //       </BrowserRouter>
// //     </React.StrictMode>
// //   );
// // }
// //
// // root.render(<Router />);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
