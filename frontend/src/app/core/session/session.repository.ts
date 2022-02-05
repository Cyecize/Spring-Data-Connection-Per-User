import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClientService} from "../../shared/http/http-client.service";
import {Endpoints} from "../../shared/http/endpoints";
import {SessionModel} from "./session.model";

@Injectable({providedIn: 'root'})
export class SessionRepository {

  constructor(private http: HttpClientService) {
  }

  public fetch(): Observable<SessionModel> {
    return this.http.get<SessionModel>(Endpoints.SESSION)
  }
}
