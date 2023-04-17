import { Badge, Box, Group, Text } from "@mantine/core";
import React from "react";
import { Customer } from "../../components/customer/customer";
import NotFound from "../NotFound/NotFound";
import { useNavigate } from "react-router-dom";

export default function ProfilePage({ customer }: { customer: Customer }) {
  const navigate = useNavigate();

  async function onClick() {
    // Dans ce cas c'est ce composant qui possède la responsabilité de savoir sur quelle route rediriger l'utilisateur
    navigate(`profile/${customer.id}`);
  }

  /*const user = useContext(UserContext);*/
  // on peut aussi utiliser le `destructuring assignment` : const { background, foreground } = useContext(ThemeContext);
  return customer ? (
    <div onClick={onClick}>
      <Group position="apart" mt="md" mb="xs">
        <Box ml={6} w={150}>
          <Text weight={500}>{customer.firstName}</Text>
          <hr />
          <Text size="sm" color="dimmed">
            Seller : {customer.lastName}
          </Text>
        </Box>
        <Badge color="pink" variant="light">
          Cart
        </Badge>
      </Group>
    </div>
  ) : (
    <NotFound />
  );
}
