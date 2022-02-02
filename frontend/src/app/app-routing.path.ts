import {RouteConfig} from "./core/routing/route-config";

export class AppRoutingPath {
  public static readonly HOME: RouteConfig = new RouteConfig('', null);
  public static readonly DATABASE_CONNECT: RouteConfig = new RouteConfig('database/connect', null);
}
