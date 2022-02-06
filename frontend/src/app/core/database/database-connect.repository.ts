import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClientSecuredService} from "../../shared/http/http-client-secured.service";
import {Endpoints} from "../../shared/http/endpoints";
import {DatabaseConnectModel} from "./database-connect.model";
import {DatabaseCreateModel} from "./database-create.model";

@Injectable({providedIn: 'root'})
export class DatabaseConnectRepository {

  constructor(private http: HttpClientSecuredService) {

  }

  public hasEstablishedConnection(): Observable<boolean> {
    return this.http.get<boolean>(Endpoints.DATABASE_HAS_ESTABLISHED_CONNECTION);
  }

  public connect(model: DatabaseConnectModel): Observable<any> {
    return this.http.post(Endpoints.DATABASE_CONNECT, model);
  }

  public selectDatabase(data: { selectedDatabase: string }): Observable<any> {
    return this.http.post(Endpoints.DATABASE_SELECT, data);
  }

  public fetchDatabases(): Observable<string[]> {
    return this.http.get(Endpoints.DATABASES);
  }

  public fetchSelectedDatabase(): Observable<{ database: string }> {
    return this.http.get(Endpoints.DATABASE_SELECTED);
  }

  public createDatabase(data: DatabaseCreateModel): Observable<any> {
    return this.http.post(Endpoints.DATABASE_CREATE, data);
  }
}
