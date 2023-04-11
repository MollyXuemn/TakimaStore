import ReactDOM from "react-dom/client";
import "./index.scss";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { MantineProvider } from "@mantine/core";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import React from "react";
import Router from "./Router";
import HomePage from "./pages/HomePage/HomePage";
import ArticlePage from "./pages/ArticlePage/ArticlePage";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

root.render(
  <>
    <Router />
    <BrowserRouter>
      <App>
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
      </App>
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
// //             {/*<Route path="/articles/:articleId" element={<ArticlePage />} />*/}
// //             {/*<Route path="sellers" element={<SellerHomePage />} />*/}
// //             {/*<Route path="cart" element={<CartPage />} />*/}
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
