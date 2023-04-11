import { Text } from "@mantine/core";
import NotFound from "../../pages/NotFound/NotFound";
import React from "react";
import { Article } from "./article";

export default function ArticleDetail({ article }: { article: Article }) {
  /*  const { articleId } = useParams();*/

  return article ? (
    <Text weight={500}>{article.product.name}</Text>
  ) : (
    <NotFound />
  );
}
