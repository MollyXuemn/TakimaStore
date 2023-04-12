import { Loader, Pagination } from "@mantine/core";
import { ErrorComponent } from "../../components/Error/Error";
import ArticleList from "../../components/article/ArticleList";
import useArticleList from "../../components/article/api/useArticleList";
import { useArticlePagination } from "../../components/article/api/searchArticle";

export default function HomePage() {
  const { articles, error, isLoading } = useArticleList();
  const {
    currentPage,
    currentPageNumber,
    totalPages,
    totalElements,
    goToPage,
    nextPage,
    previousPage,
  } = useArticlePagination();
  return (
    <div className="homePage">
      {error && <ErrorComponent error={error} />}
      {!error && isLoading ? (
        // eslint-disable-next-line react/jsx-no-undef
        <Loader />
      ) : (
        <ArticleList articles={articles}></ArticleList>
      )}
      <Pagination
        total={totalPages}
        value={currentPageNumber}
        onChange={goToPage}
      />
    </div>
  );
}
