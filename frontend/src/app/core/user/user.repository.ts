import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserModel} from "./user.model";
import {HttpClientSecuredService} from "../../shared/http/http-client-secured.service";
import {Endpoints} from "../../shared/http/endpoints";

@Injectable({providedIn: 'root'})
export class UserRepository {

  constructor(private http: HttpClientSecuredService) {

  }

  public fetch(): Observable<UserModel> {
    return this.http.get<UserModel>(Endpoints.USER_ME);
  }

  public login(data: { username: string; password: String }): Observable<any> {
    return this.http.post(Endpoints.LOGIN, data);
  }

  public logout(): Observable<any> {
    return this.http.post(Endpoints.LOGOUT, {});
  }
}
