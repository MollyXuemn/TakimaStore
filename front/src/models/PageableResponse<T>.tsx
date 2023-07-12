export interface PageableResponse<T> {
  totalPages: number;
  totalElements: number;
  size: number;
  content: T[];
  page: any;
}
