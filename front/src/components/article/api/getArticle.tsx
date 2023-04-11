// Comme toujours on en profite pour bien typer nos objets !
import { Article } from "../../../models/article";
import axios from "axios";
import articles from "../../../articles.json";
//./src/components/Article/api/getArticle.ts

// Comme toujours on en profite pour bien typer nos objets !
interface PageableResponse<T> {
  totalPages: number;
  totalElements: number;
  first: boolean;
  _embedded: any;
}

export async function getArticles() {
  return axios.get<PageableResponse<Article>>(`${Config.apiBaseUrl}/articles`);
}
