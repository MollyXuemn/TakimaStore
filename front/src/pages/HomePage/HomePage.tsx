import React from "react";
import useArticle from "../../components/article/api/useArticle";
import { Loader } from "@mantine/core";
import { ErrorComponent } from "../../components/Error/Error";
import ArticleList from "../../components/article/ArticleList";
import useArticleList from "../../components/article/api/useArticleList";

export default function HomePage() {
  const { articles, isLoading, error } = useArticleList();

  return (
    <div>
      {error && <ErrorComponent error={error} />}
      {isLoading && <Loader />}
      {articles && <ArticleList articles={articles} />}
    </div>
  );
}
