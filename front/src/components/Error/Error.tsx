import { AxiosError } from "axios";

import React from "react";
import { Alert } from "antd";

export function ErrorComponent({ error }: { error?: Error | AxiosError }) {
  if (!error) {
    return null;
  } else if (error instanceof AxiosError) {
    return (
      <Alert
        type="error"
        message="Oops, une erreur réseau est arrivée, essaie de rafraîchir la page.."
      />
    );
  }
  return (
    <Alert
      type="error"
      message="Oops, une erreur est survenue, retente plus tard."
    />
  );
}
