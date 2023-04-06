import React from 'react';
import logo from './logo.svg';
import './App.scss';
import articles from './articles.json';
import ArticleList from "./components/article/ArticleList";

function App() {

    return (

        <div className="App">
            {/*on vient donner en props nos articles tirés de articles.json*/}
            <ArticleList/>
        </div>

    );
}

export default App;
