import {Injectable} from "@angular/core";
import {HttpClientSecuredService} from "../../shared/http/http-client-secured.service";
import {TaskQuery} from "./task.query";
import {Observable} from "rxjs";
import {TaskModel} from "./task.model";
import {Page} from "../../shared/util/page";
import {Endpoints} from "../../shared/http/endpoints";
import {TaskCreate} from "./task-create";
import {RouteUtils} from "../routing/route-utils";

@Injectable({providedIn: 'root'})
export class TaskRepository {
  constructor(private http: HttpClientSecuredService) {

  }

  public fetch(query: TaskQuery): Observable<Page<TaskModel>> {
    return this.http.post<TaskQuery, Page<TaskModel>>(Endpoints.TASKS_SEARCH, query);
  }

  public save(data: TaskCreate): Observable<any> {
    return this.http.post<TaskCreate, any>(Endpoints.TASKS, data);
  }

  public update(id: number, data: TaskCreate): Observable<any> {
    return this.http.patch<TaskCreate, any>(RouteUtils.setPathParams(Endpoints.TASK, [id]), data);
  }

  public delete(id: number): Observable<any> {
    return this.http.delete(RouteUtils.setPathParams(Endpoints.TASK, [id]));
  }
}

