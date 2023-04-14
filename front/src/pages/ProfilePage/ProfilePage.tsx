import { useParams } from "react-router-dom";
import styles from "./ArticlePage.module.scss";
import ArticleDetail from "../../components/article/ArticleDetail";
import useArticle from "../../components/article/api/useArticle";
import { ErrorComponent } from "../../components/Error/Error";
import { Loader } from "@mantine/core";
import React, { useContext } from "react";

export default function ProfilePage() {
  // on peut aussi utiliser le `destructuring assignment` : const { background, foreground } = useContext(ThemeContext);
  return (
    <button style={{ background: "pink" }}>
      I am styled by theme context!
    </button>
  );
}
