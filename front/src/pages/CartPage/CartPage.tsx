import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../stores/redux";
import { removeFromCart } from "../../stores/slices/CartSlice";
import { Article } from "../../components/article/article";
import styles from "./CartPage.module.scss";
import { Badge, Box, Button, Card, Divider, Group, Text } from "@mantine/core";
import React from "react";

export default function CartPage() {
  const articles = useSelector((state: RootState) => state.cart.articles);
  const dispatch = useDispatch();

  const handleRemoveFromCart = (article: Article) => {
    dispatch(removeFromCart(article));
  };

  return (
    <>
      {articles.map((article) => (
        <div className={styles.cartPage} key={article.id}>
          <Divider orientation={"horizontal"} size="sm" />
          <Card shadow="sm" padding="lg" radius="md" withBorder>
            <Card.Section>
              <img
                className={styles.articleImage}
                src={`${Config.imageAssetsUrl}/${article.product.image}`}
                alt={article.product.name}
              />
            </Card.Section>

            <Group position="apart" mt="md" mb="xs">
              <Box ml={6} w={150}>
                <Text weight={500}>{article.product.name}</Text>
                <Text size="sm" color="dimmed">
                  Seller : {article.product.description}
                </Text>
              </Box>
              <Badge
                variant="gradient"
                gradient={{ from: "#ed6ea0", to: "#ec8c69", deg: 35 }}
                mt="md"
                radius="md"
              >
                Cart
              </Badge>
            </Group>
            <Badge ml={4} mt="md" w={100}>
              {article.product.basePrice}
            </Badge>

            <Button
              variant="gradient"
              gradient={{ from: "#ed6ea0", to: "#ec8c69", deg: 35 }}
              mt="md"
              ml="5rem"
              radius="md"
              onClick={() => handleRemoveFromCart(article)}
            >
              Remove
            </Button>
          </Card>
        </div>
      ))}
    </>
  );
}
