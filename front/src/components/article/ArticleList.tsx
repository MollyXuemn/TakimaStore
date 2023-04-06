import {useEffect, useState} from "react";
import {Article} from "../../models/article";
import styles from './ArticleList.module.scss';
import ArticleCard from "./ArticleCard";
import {getArticles} from "./api/getArticle";
import {Loader} from "@mantine/core";


export default function ArticleList() {
    const [isLoading, setLoading] = useState(false);
    const [articles, setArticles] = useState<Article[]>([]);
    useEffect(() => {
        // componentDidMount

        getArticles().then((articles) => {
            setArticles(articles);
            // une fois le setter appelé, le composant va rentrer dans l'étape d'_updating_
        });
    }, [])
    useEffect(() => {
        // componentDidMount

        if( articles.length === 0) {
            setLoading(true);
        }else{
            setLoading(false);
        }
    }, [articles])

    return <div className={styles.articleList}>

        {   isLoading ? <Loader /> :
            articles.map((article) => {
                return <ArticleCard key={article.id} article={article}  />
            })
        }
    </div>
}
