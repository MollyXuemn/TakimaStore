import { Criteria } from "../../../../models/ Http";
import axios from "axios";
import { Article } from "../../article";
import { useEffect, useState } from "react";
import { PageableResponse } from "../../../../models/PageableResponse<T>";

function searchArticles(criteria?: Criteria) {
  let url = `${Config.apiBaseUrl}/articles`;
  if (criteria?.offset !== undefined) {
    url = url + `?offset=${criteria?.offset}`;
  }
  if (criteria?.limit !== undefined) {
    if (criteria?.offset !== undefined) {
      url = url + "&";
    } else {
      url = url + "?";
    }
    url = url + `limit=${criteria?.limit}`;
  }
  if (criteria?.search !== undefined && criteria?.search !== "") {
    if (criteria?.offset !== undefined || criteria?.limit !== undefined) {
      url = url + `&search=product.name=~${criteria?.search}~`;
    } else {
      url = url + `?search=product.name=~${criteria?.search}~`;
    }
  }
  return axios
    .get<PageableResponse<Article>>(encodeURI(url))
    .then((response) => response.data);
}

export default function useSearchArticle(criteria: Criteria) {
  const [searchResult, setSearchResult] = useState<Article[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(1);

  useEffect(() => {
    searchArticles(criteria).then((response) => {
      setSearchResult(response.content);
      setTotalPages(response.page.totalPages);
      setLimit(response.page.size);
    });
  }, [criteria.search, criteria.offset, criteria.sort]);

  return { searchResult, totalPages, limit };
}
