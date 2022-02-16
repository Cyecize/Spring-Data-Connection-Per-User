export interface Page<T> {
  content: T[];
  first: boolean;
  last: boolean;
  totalElements: number;
  size: number;
  totalPages: number;
}
