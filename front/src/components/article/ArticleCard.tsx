import { Article } from "../../models/article";
import styles from "./ArticleCard.module.scss";
import { Card, Text, Badge, Button, Group, Box, Space } from "@mantine/core";
export default function ArticleCard({ article }: { article: Article }) {
  // article est passé en props de notre composant.
  return (
    <div className={styles.articleCard}>
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
            <hr />
            <Text size="sm" color="dimmed">
              Seller : {article.product.description}
            </Text>
          </Box>
          <Badge color="pink" variant="light">
            Cart
          </Badge>
        </Group>

        <Box ml={6} w={100}>
          <Button
            variant="gradient"
            gradient={{ from: "#ed6ea0", to: "#ec8c69", deg: 35 }}
            mt="md"
            radius="md"
          >
            {article.product.basePrice}
          </Button>
        </Box>
      </Card>
    </div>
  );
}
