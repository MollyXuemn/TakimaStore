import React from "react";
import "./App.scss";
import ArticleList from "./components/article/ArticleList/ArticleList";
import ErrorBoundary from "antd/es/alert/ErrorBoundary";

function App() {
  return (
    <div className="App">
      <ErrorBoundary>
        <ArticleList />
      </ErrorBoundary>
    </div>
  );
}

export default App;
