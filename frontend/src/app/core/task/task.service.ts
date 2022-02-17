import {TaskRepository} from "./task.repository";
import {TaskCreate} from "./task-create";
import {FieldError} from "../../shared/field-error/field-error";
import {BehaviorSubject, firstValueFrom} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {HttpStatus} from "../../shared/http/http-status";
import {BindingErrorResponse} from "../../shared/field-error/binding-error-response";
import {Injectable} from "@angular/core";
import {Page} from "../../shared/util/page";
import {TaskModel} from "./task.model";
import {TaskQuery} from "./task.query";

@Injectable({providedIn: 'root'})
export class TaskService {

  private readonly tasks: BehaviorSubject<Page<TaskModel>> = new BehaviorSubject<Page<TaskModel>>(
    {content: [], first: true, last: true, size: 0, totalElements: 0, totalPages: 0}
  );
  public readonly tasks$ = this.tasks.asObservable();

  constructor(private repository: TaskRepository) {
  }

  public async createTask(data: TaskCreate): Promise<FieldError[]> {
    try {
      await firstValueFrom(this.repository.save(data));
      return [];
    } catch (error: HttpErrorResponse | any) {
      if (error.status === HttpStatus.NOT_ACCEPTABLE) {
        return (error.error as BindingErrorResponse).fieldErrors;
      } else {
        return [{
          message: error.error.message,
          field: ''
        }];
      }
    }
  }

  public loadTasks(query: TaskQuery): void {
    this.repository.fetch(query).subscribe(value => this.tasks.next(value));
  }
}
