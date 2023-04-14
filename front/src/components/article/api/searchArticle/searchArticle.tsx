import { Criteria } from "../../../../models/ Http";
import axios from "axios";
import { Article } from "../../article";
import { useEffect, useState } from "react";
import { PageableResponse } from "../../../../models/PageableResponse<T>";

function searchArticles(criteria?: Criteria) {
  return axios.get<PageableResponse<Article>>(
    encodeURI(
      `${Config.apiBaseUrl}/articles?offset=${criteria?.offset}&limit=${criteria?.limit}&search=product.name=~${criteria?.search}`
    )
  );
}

export default function useSearchArticle(criteria: Criteria) {
  const [searchResult, setSearchResult] = useState<Article[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [limit, setLimit] = useState(1);

  useEffect(() => {
    searchArticles(criteria).then((response) => {
      setSearchResult(response.data._embedded?.articles);
      setTotalPages(response.data.page.totalPages);
      setLimit(response.data.page.size);
    });
  }, [criteria.search, criteria.offset, criteria.sort]);

  return { searchResult, totalPages, limit };
}
