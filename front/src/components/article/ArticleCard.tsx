import { Article } from "./article";
import styles from "./ArticleCard.module.scss";
import { Card, Text, Badge, Button, Group, Box } from "@mantine/core";
import { Link, useNavigate } from "react-router-dom";
import { Divider } from "antd";
import { useDispatch } from "react-redux";
import { addToCart } from "../../stores/slices/CartSlice";

export default function ArticleCard({ article }: { article: Article }) {
  const dispatch = useDispatch();
  // article est passé en props de notre composant.
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
              Seller : {article.description}
            </Text>
          </Box>
          <Button
            variant="gradient"
            gradient={{ from: "#ed6ea0", to: "#ec8c69", deg: 35 }}
            mt="md"
            radius="md"
            onClick={() => dispatch(addToCart(article))}
          >
            Cart
          </Button>
        </Group>

        <Box ml={6} w={100}>
          {article.product.basePrice}
        </Box>
      </Card>
    </div>
  );
}
