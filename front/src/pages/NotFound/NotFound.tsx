import { Center, Image } from "@mantine/core";

export default function NotFound() {
  return (
    <Center>
      <Image width={600} src="/Error404.webp" alt="error 404" withPlaceholder />
    </Center>
  );
}
