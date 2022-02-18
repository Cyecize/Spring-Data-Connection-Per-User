import {Injectable} from "@angular/core";
import {SessionRepository} from "./session.repository";
import {CookieService} from "ngx-cookie-service";
import {HttpHeaderType} from "../../shared/http/http-header";
import {map, Observable} from "rxjs";

@Injectable({providedIn: 'root'})
export class SessionService {

  constructor(private repository: SessionRepository,
              private cookieService: CookieService) {
  }

  public refreshSession(): Observable<string> {
    return this.repository.fetch().pipe(map(value => {
      this.cookieService.set(HttpHeaderType.SESSION_TOKEN, value.sessionId, 1, '/');
      return value.sessionId;
    }));
  }
}
