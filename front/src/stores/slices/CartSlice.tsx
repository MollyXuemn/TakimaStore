import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Article } from "../../components/article/article";
import { RootState } from "../redux";

const cartSlice = createSlice({
  name: "cart",
  initialState: {
    articles: [] as Article[],
  },
  reducers: {
    addToCart: (state, action: PayloadAction<Article>) => {
      // action.payload contient l'article à ajouter au store dans state.articles
      // state contient l'état précédent de l'application
      return { ...state, articles: [...state.articles, action.payload] };
    },
    removeFromCart: (state, action: PayloadAction<Article>) => {
      // action.payload contient l'article à supprimer du store dans state.articles
      return {
        ...state,
        articles: state.articles.filter(
          (article) => article.id !== action.payload.id
        ),
      };
    },
  },
});

export const { addToCart, removeFromCart } = cartSlice.actions;

export default cartSlice.reducer;
