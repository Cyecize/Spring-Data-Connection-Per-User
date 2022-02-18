import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {catchError, map, Observable, switchMap, throwError} from "rxjs";
import {SessionService} from "../../../core/session/session.service";
import {HttpHeaderType} from "../http-header";
import {HttpStatus} from "../http-status";

@Injectable({providedIn: 'root'})
export class HttpRetryInterceptor implements HttpInterceptor {

  constructor(private sessionService: SessionService) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(error => {
        // Check if request has to be retried.
        if (error.status == HttpStatus.FAILED_DEPENDENCY && error.error.message === 'No Session!') {
          return this.sessionService.refreshSession().pipe(
            map(value => req = req.clone({
              headers: req.headers.set(HttpHeaderType.SESSION_TOKEN, value)
            })),
            switchMap(() => next.handle(req))
          )
        }

        return throwError(error);
      })
    )
  }
}



