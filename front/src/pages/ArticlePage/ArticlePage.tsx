import { useParams } from "react-router-dom";
import styles from "./ArticlePage.module.scss";
import ArticleDetail from "../../components/article/ArticleDetail";
import useArticle from "../../components/article/api/useArticle";
import { ErrorComponent } from "../../components/Error/Error";
import { Loader } from "@mantine/core";

export default function ArticlePage() {
  const { articleId } = useParams();
  const { article, isLoading, error } = useArticle(
    parseInt(articleId ?? "0")
  ); /*it enforce the articleId to be string if it does not exist */

  return (
    <div className={styles.articlePage}>
      {<ErrorComponent error={error} />}
      {!error && isLoading ? (
        <Loader />
      ) : (
        <ArticleDetail article={article!}></ArticleDetail>
      )}
    </div>
  );
}
