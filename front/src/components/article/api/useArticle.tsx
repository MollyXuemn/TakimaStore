import { useEffect, useState } from "react";
import { getArticle, getArticles } from "./getArticle";
import { Article } from "../article";
import { AxiosError } from "axios";

export default function useArticle(articleId: number) {
  const [isLoading, setLoading] = useState(false);
  const [article, setArticle] = useState<Article | undefined>(undefined);
  // On pense toujours à typer notre variable !
  const [error, setError] = useState<Error | AxiosError | undefined>();

  function fetch() {
    setLoading(true);
    return getArticle(articleId)
      .then((response) => {
        //debugger
        //console.log(article, setArticle)
        setArticle(response.data);
        console.log(response);
      })
      .catch((e: Error | AxiosError) => {
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

  return { article, isLoading, error };
}
