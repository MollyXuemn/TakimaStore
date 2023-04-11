import { useParams } from "react-router-dom";
import styles from "./ArticlePage.module.scss";
import ArticleDetail from "../../components/article/ArticleDetail";
import useArticleList from "../../components/article/api/useArticleList";
import useArticle from "../../components/article/api/useArticle";
import { ErrorComponent } from "../../components/Error/Error";
import ArticleCard from "../../components/article/ArticleCard";
import { isError } from "util";
import { Loader } from "@mantine/core";

export default function ArticlePage() {
  const { articleId } = useParams();
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  const { article, isLoading, error } = useArticle(parseInt(articleId || "0"));

  return (
    <div className={styles.articlePage}>
      {/* eslint-disable-next-line react/jsx-no-undef */}
      {<ErrorComponent error={error} />}
      {!error && isLoading ? (
        // eslint-disable-next-line react/jsx-no-undef
        <Loader />
      ) : (
        <ArticleDetail article={article!}></ArticleDetail>
      )}
    </div>
  );
}
