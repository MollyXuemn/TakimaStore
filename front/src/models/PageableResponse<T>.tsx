export interface PageableResponse<T> {
  totalPages: number;
  totalElements: number;
  size: number;
  _embedded: any;
  page: any;
}
