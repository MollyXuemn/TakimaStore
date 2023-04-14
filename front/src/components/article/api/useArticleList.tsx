import { useEffect, useState } from "react";
import { getArticles } from "./getArticle";
import { Article } from "../article";
import { AxiosError } from "axios";

export default function useArticleList() {
  const [isLoading, setLoading] = useState(false);
  const [articles, setArticles] = useState<Article[]>([]);
  // On pense toujours à typer notre variable !
  const [error, setError] = useState<Error | AxiosError | undefined>();

  function fetch() {
    setLoading(true);
    return getArticles()
      .then((response) => {
        //debugger
        //console.log(articles, setArticles)
        setArticles(response.data["_embedded"].articles);
        console.log(response);
      })
      .catch((e: Error | AxiosError) => {
        // gestion d'erreur dans la partie suivante
        console.error("Error when fetching Article List !" + e);
        setError(e);
      })
      .finally(() => {
        setLoading(false);
      });
  }

  useEffect(() => {
    fetch();
  }, []);

  return { articles, isLoading, error };
}