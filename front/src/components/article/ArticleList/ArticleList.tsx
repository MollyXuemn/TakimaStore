import React from "react";
import styles from "./ArticleList.module.scss";
import ArticleCard from "../ArticleCard";
import { Loader } from "@mantine/core";
import useArticleList from "../api/useArticleList";
import { ErrorComponent } from "../../Error/Error";

export default function ArticleList() {
  const { isLoading, articles, error } = useArticleList();

  let content;
  if (!error) {
    if (isLoading) {
      content = <Loader />;
    } else {
      content = articles.map((article) => {
        return <ArticleCard key={article.id} article={article} />;
      });
    }
  }

  return (
    <div className={styles.articleList}>
      {<ErrorComponent error={error} />}
      {/* utilise la librarie de composant installée précédemment pour le spinner. */}
      {content}
    </div>
  );
}
