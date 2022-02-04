import {Injectable} from "@angular/core";
import {DatabaseProviderModel} from "./database-provider.model";
import {HttpClientService} from "../../shared/http/http-client.service";
import {Observable} from "rxjs";
import {Endpoints} from "../../shared/http/endpoints";

@Injectable({providedIn: 'root'})
export class DatabaseProviderRepository {

  constructor(private http: HttpClientService) {
  }

  public fetch(): Observable<DatabaseProviderModel[]> {
    return this.http.get(Endpoints.DATABASE_PROVIDERS);
  }

}
