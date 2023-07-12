import styles from "./ArticleList.module.scss";
import ArticleCard from "./ArticleCard";
import { Link } from "react-router-dom";
import { Article } from "./article";

export default function ArticleList({ articles }: { articles: Article[] }) {
  return (
    <div className={styles.articleList}>
      {articles.map((article) => (
        <ArticleCard key={article.id} article={article} />
      ))}
    </div>
  );
}
