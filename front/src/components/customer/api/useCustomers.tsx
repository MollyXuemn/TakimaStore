import { useEffect, useState } from "react";
import { Customer } from "../customer";
import { AxiosError } from "axios";

export default function useCustomers() {
  const [isLoading, setLoading] = useState(false);
  // On pense toujours à typer notre variable !
  const [error, setError] = useState<Error | AxiosError | undefined>();
  const [user, setUser] = useState<Customer>();

  useEffect(() => {
    fetch("/customers/123") // use whatever userId you want
      .then((response) => response.json())
      .then((data) => {
        setUser(data);
        setLoading(false);
      });
  }, []);

  return (
    <UserContext.Provider value={{ user, loading }}>
      {/* rest of your app */}
    </UserContext.Provider>
  );

  return { customers, isLoading, error };
}
