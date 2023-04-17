import { useEffect, useState } from "react";
import { Customer } from "../customer";
import { AxiosError } from "axios";
import { getCustomer } from "./getCustomer";

export default function useCustomer(userId: number) {
  const [isLoading, setLoading] = useState(false);
  // On pense toujours à typer notre variable !
  const [error, setError] = useState<Error | AxiosError | undefined>();
  const [customer, setCustomer] = useState<Customer | undefined>(undefined);
  function fetch() {
    setLoading(true);

    return getCustomer(userId)
      .then((response) => {
        setCustomer(response.data);
        console.log(response);
      })
      .catch((e: Error | AxiosError) => {
        // Nous sommes sûr ici que l'erreur sera logée
        console.error("Error while fetching articleList: ", e);
        setError(e);
      })
      .finally(() => {
        setLoading(false);
      });
  }
  useEffect(() => {
    fetch();
  }, []);

  /*  return (
      <UserContext.Provider value={{ customer, loading }}>
        {/!* rest of your app *!/}
      </UserContext.Provider>
    );*/

  return { customer, isLoading, error };
}
