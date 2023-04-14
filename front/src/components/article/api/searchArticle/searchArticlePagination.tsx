import useSearchArticle from "./searchArticle";
import { useState } from "react";

const ELEMENT_PER_PAGE = 10;

export function useArticlePagination() {
  const [currentPageNumber, setCurrentPageNumber] = useState(0);
  const [search, setSearch] = useState<string>("");

  const { searchResult, totalPages, limit } = useSearchArticle({
    offset: ELEMENT_PER_PAGE * currentPageNumber,
    //(currentPageNumber === 1 ? currentPageNumber : currentPageNumber - 1),
    limit: ELEMENT_PER_PAGE,
    search,
  });

  function searchValue(value: string) {
    setCurrentPageNumber(0);
    setSearch(value);
  }

  function previousPage() {
    if (currentPageNumber > 1) {
      setCurrentPageNumber(currentPageNumber - 1);
    }
  }

  function nextPage() {
    setCurrentPageNumber(currentPageNumber + 1);
  }

  function goToPage(pageNumber: number) {
    setCurrentPageNumber(pageNumber);
  }

  return {
    currentPage: searchResult,
    currentPageNumber,
    totalPages,
    totalElements: searchResult,
    goToPage,
    nextPage,
    previousPage,
    searchValue,
    search,
  };
}
