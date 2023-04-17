import { Pagination } from "@mantine/core";
import ArticleList from "../../components/article/ArticleList";
import { useArticlePagination } from "../../components/article/api/searchArticle/searchArticlePagination";
import styles from "./Home.module.scss";
import Search from "antd/es/input/Search";
import { ConfigProvider } from "antd";

export default function HomePage() {
  // const { articles, error, isLoading } = useCustomer();
  const {
    currentPage,
    currentPageNumber,
    totalPages,
    totalElements,
    goToPage,
    nextPage,
    previousPage,
    searchValue,
    search,
  } = useArticlePagination();

  function onSearch(value: string) {
    console.log(search);
    searchValue(value);
  }

  return (
    <div className={styles.homePage}>
      <div style={{ margin: "10px" }}>
        <ConfigProvider
          theme={{
            token: {
              colorPrimary: "#f75ea0",
            },
          }}
        >
          <Search
            placeholder="input search text"
            allowClear
            enterButton
            type="warning"
            style={{ width: 300 }}
            onSearch={onSearch}
          />
        </ConfigProvider>
      </div>

      {
        <>
          {currentPage && <ArticleList articles={currentPage}></ArticleList>}
          <Pagination
            total={totalPages}
            value={currentPageNumber}
            onChange={goToPage}
            position="center"
          />
        </>
      }
    </div>
  );
}
