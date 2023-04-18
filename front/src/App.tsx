import "./App.scss";
import { Outlet } from "react-router-dom";
import NavigationBar from "./components/Shell/NavigationBar/NavigationBar";
import { Loader, MantineProvider } from "@mantine/core";
import FootBar from "./components/Shell/FootBar/FootBar";
import { createContext } from "react";
import { Customer } from "./components/customer/customer";
import useCustomer from "./components/customer/api/useCustomer";
import { ErrorComponent } from "./components/Error/Error";
import { setupStore } from "./stores/redux";
import { Provider } from "react-redux";
/*type Customer = {
  firstName: string;
  lastName: string;
  email: string;
};*/

export const UserContext = createContext<Customer | undefined>(undefined);
const store = setupStore();
function App() {
  const { customer, isLoading, error } = useCustomer(3);

  if (isLoading) {
    return <Loader />;
  }
  if (error) {
    return <ErrorComponent error={error} />;
  }

  return (
    <MantineProvider withGlobalStyles withNormalizeCSS>
      <Provider store={store}>
        <UserContext.Provider value={customer}>
          <div className="App">
            <NavigationBar />
            {/* Ce composant Outlet affichera la route actuelle en fonction de l'URL */}
            {/* Par défaut le composant affiché est ArticlePageList */}
            <Outlet />
            <FootBar />
          </div>
        </UserContext.Provider>
      </Provider>
    </MantineProvider>
  );
}

export default App;
