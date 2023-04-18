import {
  Avatar,
  Badge,
  Box,
  Container,
  Group,
  Space,
  Text,
} from "@mantine/core";
import React, { useContext } from "react";
import NotFound from "../NotFound/NotFound";
import styles from "./ProfilePage.module.scss";
import { UserContext } from "../../App";

export default function ProfilePage() {
  const user = useContext(UserContext);
  return user ? (
    <div className={styles.profilePage}>
      <Group position={"center"} ml={6}>
        <Box
          h={800}
          w={600}
          sx={(theme) => ({
            backgroundColor:
              theme.colorScheme === "dark"
                ? theme.colors.dark[5]
                : theme.colors.gray[1],
          })}
        >
          <Avatar ml={280} mt={20} color="cyan" radius="xl">
            MK
          </Avatar>
          {user && (
            <Text mt={20} size="md" color="dimmed">
              FirstName: {user.firstName}
            </Text>
          )}
          {user && (
            <Text weight={500} fw={700} mt={20}>
              LastName : {user.lastName}
            </Text>
          )}
          <Space w="md" />
          {user && (
            <Text mt={20} weight={500}>
              Email: {user.email}
            </Text>
          )}
          {user && (
            <Text mt={20} weight={500}>
              address: {user.address.street}
            </Text>
          )}
          <Space w="md" />
          {user && (
            <Text mt={20} weight={500}>
              city: {user.address.city}
            </Text>
          )}
          <Space w="md" />
          {user && (
            <Text mt={20} weight={500}>
              zipcode: {user.address.zipcode}
            </Text>
          )}
          <Space w="md" />
          {user && (
            <Text mt={20} weight={500}>
              country: {user.address.country}
            </Text>
          )}
        </Box>
      </Group>
    </div>
  ) : (
    <NotFound />
  );
}
