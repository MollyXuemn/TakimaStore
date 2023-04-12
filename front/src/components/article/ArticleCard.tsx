import { Article } from "./article";
import styles from "./ArticleCard.module.scss";
import { Card, Text, Badge, Button, Group, Box } from "@mantine/core";
import { useNavigate } from "react-router-dom";
import { Divider } from "antd";

export default function ArticleCard({ article }: { article: Article }) {
  const navigate = useNavigate();

  async function onClick() {
    // Dans ce cas c'est ce composant qui possède la responsabilité de savoir sur quelle route rediriger l'utilisateur
    navigate(`articles/${article.id}`);
  }

  // article est passé en props de notre composant.
  return (
    <div className={styles.articleCard}>
      <Divider orientation="left"></Divider>

      <Card shadow="sm" padding="lg" radius="md" withBorder onClick={onClick}>
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
