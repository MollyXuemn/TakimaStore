import "./App.scss";
import { BrowserRouter, Outlet } from "react-router-dom";
import NavigationBar from "./components/Shell/NavigationBar";
import { MantineProvider } from "@mantine/core";
import React from "react";

function App() {
  return (
    <>
      <MantineProvider withGlobalStyles withNormalizeCSS>
        <div className="App">
          <NavigationBar />
          <Outlet />
        </div>
      </MantineProvider>
    </>
  );
}

export default App;
