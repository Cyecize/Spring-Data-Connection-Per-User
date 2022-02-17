import {TaskRepository} from "./task.repository";
import {TaskCreate} from "./task-create";
import {FieldError} from "../../shared/field-error/field-error";
import {firstValueFrom} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {HttpStatus} from "../../shared/http/http-status";
import {BindingErrorResponse} from "../../shared/field-error/binding-error-response";
import {Injectable} from "@angular/core";

@Injectable({providedIn: 'root'})
export class TaskService {
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
}
