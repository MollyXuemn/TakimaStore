import { useParams } from "react-router-dom";
import styles from "./CartPage.module.scss";
import ArticleDetail from "../../components/article/ArticleDetail";
import useArticle from "../../components/article/api/useArticle";
import { ErrorComponent } from "../../components/Error/Error";
import { Loader } from "@mantine/core";

export default function CartPage() {
  return <div className={styles.cartPage}></div>;
}
