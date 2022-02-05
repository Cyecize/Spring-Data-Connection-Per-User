import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClientSecuredService} from "../../shared/http/http-client-secured.service";
import {Endpoints} from "../../shared/http/endpoints";
import {DatabaseConnectModel} from "./database-connect.model";

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

  public fetchDatabases(): Observable<string[]> {
    return this.http.get(Endpoints.DATABASES);
  }
}
