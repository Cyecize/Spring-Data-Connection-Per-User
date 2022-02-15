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
import {DatabaseConnectService} from "../database/database-connect.service";

@Injectable({providedIn: 'root'})
export class HasSelectedDatabaseGuard implements CanActivate, CanActivateChild {

  constructor(private databaseService: DatabaseConnectService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.databaseService.hasSelectedDatabase().pipe(map(hasSelectedDb => {
      if (hasSelectedDb) {
        return true;
      }

      return this.router.createUrlTree([AppRoutingPath.DATABASE_SELECT.absolutePath]);
    }));
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canActivate(childRoute, state);
  }
}
