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
      return { ...state, articles: [...state.articles, action.payload] };
    },
    removeFromCart: (state, action: PayloadAction<Article>) => {
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
