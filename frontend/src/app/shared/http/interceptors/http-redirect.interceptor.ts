import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {RouteNavigator} from "../../../core/routing/route-navigator.service";
import {HttpStatus} from "../http-status";
import {AppRoutingPath} from "../../../app-routing.path";

@Injectable({providedIn: 'root'})
export class HttpRedirectInterceptor implements HttpInterceptor {

  constructor(private nav: RouteNavigator) {
  }


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(error => {
      // Check if request has to be redirected
      if (error.status == HttpStatus.FAILED_DEPENDENCY && error.error.message === 'No Connection!') {
        this.nav.navigate(AppRoutingPath.DATABASE_CONNECT)
      }

      return throwError(error);
    }));
  }

}
