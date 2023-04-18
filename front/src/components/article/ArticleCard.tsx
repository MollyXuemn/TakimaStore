import { Article } from "./article";
import styles from "./ArticleCard.module.scss";
import { Badge, Box, Button, Card, Group, Text } from "@mantine/core";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { addToCart } from "../../stores/slices/CartSlice";
import React from "react";
import { ShoppingCartOutlined } from "@ant-design/icons";

export default function ArticleCard({ article }: { article: Article }) {
  const dispatch = useDispatch();
  return (
    <div className={styles.articleCard}>
      <Card shadow="sm" padding="lg" radius="md" withBorder>
        <Card.Section>
          <Link to={`/articles/${article.id}`}>
            <img
              className={styles.articleImage}
              src={`${Config.imageAssetsUrl}/${article.product.image}`}
              alt={article.product.name}
            />
          </Link>
        </Card.Section>

        <Group position="apart" mt="md" mb="xs">
          <Box ml={6} w={150}>
            <Text weight={500}>{article.product.name}</Text>
            <hr />
            <Text size="sm" color="dimmed">
              Description : {article.description}
            </Text>
          </Box>

          <Button
            variant="gradient"
            gradient={{ from: "#ed6ea0", to: "#ec8c69", deg: 35 }}
            mt="md"
            radius="md"
            onClick={() => dispatch(addToCart(article))}
          >
            <ShoppingCartOutlined />
            Cart
          </Button>
        </Group>

        <Box ml={6} mt={30} w={100}>
          <Badge color="pink" variant="light" size="md" fullWidth>
            {article.product.basePrice}
          </Badge>
        </Box>
      </Card>
    </div>
  );
}
