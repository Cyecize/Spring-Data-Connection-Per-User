import {RouteConfig} from "./core/routing/route-config";

export class AppRoutingPath {
  public static readonly HOME: RouteConfig = new RouteConfig('', null);
  public static readonly DATABASE_CONNECT: RouteConfig = new RouteConfig('database/connect', null);
  public static readonly DATABASE_SELECT: RouteConfig = new RouteConfig('database/select', null);
  public static readonly DATABASE_CREATE: RouteConfig = new RouteConfig('database/create', null);
  public static readonly LOGIN: RouteConfig = new RouteConfig('login', null);
}
