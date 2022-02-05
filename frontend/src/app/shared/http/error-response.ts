export interface ErrorResponse {
  timestamp: Date;
  path: string;
  status: number;
  error: string;
  message: string;
}
