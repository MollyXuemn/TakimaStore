import { Criteria } from "../../../models/ Http";
import axios from "axios";
import { Article } from "../article";
import { useEffect, useState } from "react";
import { PageableResponse } from "../../../models/PageableResponse<T>";

function searchArticles(criteria?: Criteria) {
  return axios.get<PageableResponse<Article>>(
    encodeURI(`${Config.apiBaseUrl}/articles?offset=${criteria?.offset}`)
  );
}

function useSearchArticle(criteria: Criteria) {
  const [searchResult, setSearchResult] = useState<Article[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(1);

  useEffect(() => {
    searchArticles(criteria).then((response) => {
      setSearchResult(response.data["_embedded"].articles);
      setTotalPages(response.data["page"].totalPages);
      setLimit(response.data["page"].size);
    });
  }, [criteria.search, criteria.offset, criteria.sort]);

  return { searchResult, totalPages, limit };
}

const ELEMENT_PER_PAGE = 10;

export function useArticlePagination() {
  const [currentPageNumber, setCurrentPageNumber] = useState(1);

  const { searchResult, totalPages, limit } = useSearchArticle({
    offset:
      ELEMENT_PER_PAGE *
      (currentPageNumber === 1 ? currentPageNumber : currentPageNumber - 1),
  });

  function previousPage() {
    useEffect(() => {
      const previousPage = currentPageNumber - 1;

      setCurrentPageNumber(previousPage);
      // Update the current searchResult
    }, [currentPageNumber]);
  }

  function nextPage() {
    useEffect(() => {
      const nextPage = currentPageNumber + 1;
      // Update the current page number
      setCurrentPageNumber(nextPage);
    }, [currentPageNumber]);
  }

  function goToPage(pageNumber: number) {
    useEffect(() => {
      // Update the current page number state
      setCurrentPageNumber(pageNumber);
    }, [pageNumber]);
  }

  return {
    currentPage: searchResult,
    currentPageNumber,
    totalPages,
    totalElements: searchResult,
    goToPage,
    nextPage,
    previousPage,
  };
}
