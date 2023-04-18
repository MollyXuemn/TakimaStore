import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Article } from "../../components/article/article";

const cartSlice = createSlice({
  name: "cart",
  initialState: {
    articles: [] as Article[],
  },
  reducers: {
    addToCart: (state, action: PayloadAction<Article>) => {
      // TODO implement this
      // action.payload contient l'article à ajouter au store dans state.articles
      // state contient l'état précédent de l'application
      state.articles.push(action.payload);

      removeFromCart: (state, action: PayloadAction<Article>) => {
        // TODO implement this
        state.articles.remove(action.payload);
      };
    },
  },
});

export const { addToCart, removeFromCart } = cartSlice.actions;

export default cartSlice.reducer;
