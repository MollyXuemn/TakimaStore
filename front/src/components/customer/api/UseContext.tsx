import { createContext, useState, useEffect } from "react";

type User = {
  name: string;
  email: string;
  // other user fields...
};

type UserContextType = {
  user?: User;
  loading: boolean;
};

const initialUserContext: UserContextType = {
  user: undefined,
  loading: true,
};

export const UserContext = createContext<UserContextType>(initialUserContext);
