// Comme toujours on en profite pour bien typer nos objets !
import { Article } from "../article";
import axios from "axios";
//./src/components/Article/api/getArticle.ts

// Comme toujours on en profite pour bien typer nos objets !
interface PageableResponse<T> {
  totalPages: number;
  totalElements: number;
  first: boolean;
  _embedded: any;
}

export async function getArticle(articleId: number) {
  return axios.get<Article>(`${Config.apiBaseUrl}/articles/${articleId}`);
}
export async function getArticles() {
  return axios.get<PageableResponse<Article>>(`${Config.apiBaseUrl}/articles`);
}
