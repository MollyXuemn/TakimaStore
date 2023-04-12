import "./App.scss";
import { BrowserRouter, Outlet } from "react-router-dom";
import NavigationBar from "./components/Shell/NavigationBar/NavigationBar";
import { MantineProvider } from "@mantine/core";
import FootBar from "./components/Shell/FootBar/FootBar";

function App() {
  return (
    <MantineProvider withGlobalStyles withNormalizeCSS>
      <div className="App">
        <NavigationBar />
        {/* Ce composant Outlet affichera la route actuelle en fonction de l'URL */}
        {/* Par défaut le composant affiché est ArticlePageList */}
        <Outlet />
        <FootBar />
      </div>
    </MantineProvider>
  );
}

export default App;
