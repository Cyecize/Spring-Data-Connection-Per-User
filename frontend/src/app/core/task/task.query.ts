export interface TaskQuery {
  page: number;
  size: number;
  orderBy: string;
  direction: string;
  hasDueDate?: boolean;
  inProgress?: boolean;
  description?: string;
}
