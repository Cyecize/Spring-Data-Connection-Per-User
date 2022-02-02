import {NavigationExtras, Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {RouteConfig} from './route-config';
import {RouteUtils} from './route-utils';

@Injectable({providedIn: 'root'})
export class RouteNavigator {

  constructor(private router: Router) {

  }

  public navigate(route: RouteConfig, pathVariables: any[] | { [name: string]: any } | null = null, extras: NavigationExtras = {}): void {
    // TODO: add params here.
    const url = RouteUtils.setPathParams(route.absolutePath, pathVariables);

    this.router.navigate([url], extras);
  }
}
