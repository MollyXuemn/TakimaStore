import { Criteria } from "../../../models/ Http";
import axios from "axios";
import { Article } from "../article";
import { useEffect, useState } from "react";

interface PageableResponse<T> {
  totalPages: number;
  totalElements: number;
  first: boolean;
  _embedded: any;
}

/*function searchArticles(criteria?: Criteria) {
  return axios
    .get<PageableResponse<Article>>(
      encodeURI(`${Config.apiBaseUrl}/articles?offset=${criteria?.offset}`)
    )
    .then((response) => response.data["_embedded"]);
}

function useSearchArticle(criteria: Criteria) {
  const [searchResult, setSearchResult] = useState<Article[]>([]);

  useEffect(() => {
    searchArticles(criteria).then((response) => {
      setSearchResult(response.data["_embedded"]);
    });
  }, [criteria.search, criteria.offset, criteria.sort]);

  return { searchResult };
}

const ELEMENT_PER_PAGE = 10;*/

export function useArticlePagination() {
  return "";
  /*const [currentPageNumber, setCurrentPageNumber] = useState(1);
  const [totalPages, setTotalPages] = useState(0);

  const { searchResult } = useSearchArticle({
    offset: ELEMENT_PER_PAGE * (currentPageNumber - 1),
  });

  function previousPage() {}

  function nextPage() {}

  function goToPage(pageNumber: number) {}

  return {
    currentPage: searchResult,
    currentPageNumber,
    totalPages,
    goToPage,
    nextPage,
    previousPage,
  };*/
}
