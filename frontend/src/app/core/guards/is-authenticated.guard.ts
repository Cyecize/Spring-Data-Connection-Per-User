import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {AppRoutingPath} from '../../app-routing.path';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {UserService} from "../user/user.service";

@Injectable({providedIn: 'root'})
export class IsAuthenticatedGuard implements CanActivate, CanActivateChild {

  constructor(private userService: UserService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.userService.isLoggedIn().pipe(map(isLoggedIn => {
      if (isLoggedIn) {
        return true;
      }

      return this.router.createUrlTree([AppRoutingPath.LOGIN.absolutePath]);
    }));
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canActivate(childRoute, state);
  }
}
