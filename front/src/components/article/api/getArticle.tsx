// Comme toujours on en profite pour bien typer nos objets !
import {Article} from "../../../models/article";
import axios from "axios";
import articles from '../../../articles.json';
export async function getArticles(): Promise<Article[]> {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve(articles);
        }, 1200); // simulation du réseau avec un temps d'attente
    });
}
