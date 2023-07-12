import { Card, Text, Badge, Button, Group, Box } from "@mantine/core";
import NotFound from "../../pages/NotFound/NotFound";
import React from "react";
import { Article } from "./article";
import styles from "./ArticleDetail.module.scss";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { addToCart } from "../../stores/slices/CartSlice";
export default function ArticleDetail({
  article,
  onCart,
}: {
  article: Article;
  onCart: (article: Article) => void;
}) {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  async function onClick() {
    // Dans ce cas c'est ce composant qui possède la responsabilité de savoir sur quelle route rediriger l'utilisateur
    navigate(`articles/${article.id}`);
  }

  return article ? (
    <div className={styles.articleDetails}>
      <Card shadow="sm" padding="lg" radius="md" withBorder>
        <Card.Section>
          <img
            className={styles.articleImage}
            src={`${Config.imageAssetsUrl}/${article.product.image}`}
            alt={article.product.name}
            onClick={onClick}
          />
        </Card.Section>

        <Group position="apart" mt="md" mb="xs">
          <Box ml={6} w={150}>
            <Text weight={500}>{article.product.name}</Text>
            <hr />
            <Text size="sm" color="dimmed">
              Description : {article.product.description}
            </Text>
          </Box>
          <Badge color="pink" variant="light">
            Cart
          </Badge>
        </Group>

        <Box ml={6} w={100}>
          <Badge color="pink" variant="light" size="md" fullWidth>
            {article.product.basePrice}
          </Badge>

          <Button
            variant="gradient"
            gradient={{ from: "#ed6ea0", to: "#ec8c69", deg: 35 }}
            mt="md"
            ml={40}
            radius="md"
            onClick={(e) => {
              dispatch(addToCart(article));
            }}
          >
            Ajouter au panier
          </Button>
        </Box>
      </Card>
    </div>
  ) : (
    <NotFound />
  );
}
