// ./stores/redux.ts
import {
  combineReducers,
  configureStore,
  PreloadedState,
} from "@reduxjs/toolkit";
import { createLogger } from "redux-logger";

import cart from "./slices/CartSlice";

import { useDispatch } from "react-redux";

export const reducer = combineReducers({
  cart,
});

const logger = createLogger({ collapsed: true });

export const setupStore = (preloadedState?: PreloadedState<RootState>) => {
  return configureStore({
    reducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(logger),
    preloadedState,
  });
};

// see how our store is typed !
export type RootState = ReturnType<typeof reducer>;
export type AppStore = ReturnType<typeof setupStore>;
export type AppDispatch = AppStore["dispatch"];
export const useAppDispatch = () => useDispatch<AppDispatch>();
