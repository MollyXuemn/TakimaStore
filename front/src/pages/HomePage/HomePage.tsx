import React from "react";
import { Loader } from "@mantine/core";
import { ErrorComponent } from "../../components/Error/Error";
import ArticleList from "../../components/article/ArticleList";
import useArticleList from "../../components/article/api/useArticleList";
import { useArticlePagination } from "../../components/article/api/searchArticle";

export default function HomePage() {
  const { articles, isLoading, error } = useArticleList();
  /*function HomePage() {
    const {
      currentPage,
      currentPageNumber,
      pageSize,
      limit,
      goToPage,
      previousPage,
      nextPage,
    } = useArticlePagination();
  }*/
  return (
    <div className="homePage">
      {error && <ErrorComponent error={error} />}
      {isLoading && <Loader />}
      {articles && <ArticleList articles={articles} />}
    </div>
  );
}
