export interface TaskModel {
  id: number;
  description: string;
  inProgress: boolean;
  dueDate?: string;
  createDate: string;
}
