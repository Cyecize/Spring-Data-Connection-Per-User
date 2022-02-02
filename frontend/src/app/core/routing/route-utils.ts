export class RouteUtils {
  private static setPathParamsArray(url: string, arr: any[]): string {
    const pattern = /:[a-zA-Z_-]+/;

    for (const arrElement of arr) {
      if (!pattern.test(url)) {
        return url;
      }

      url = url.replace(pattern, arrElement);
    }

    return url;
  }

  private static setPathParamsObject(url: string, params: { [name: string]: any }): string {
    for (const paramKey of Object.keys(params)) {
      url = url.replace(':' + paramKey, params[paramKey]);
    }

    return url;
  }

  public static setPathParams(url: string, pathVariables: any[] | { [name: string]: any } | null = null): string {
    if (pathVariables !== null) {
      if (Array.isArray(pathVariables)) {
        url = RouteUtils.setPathParamsArray(url, pathVariables);
      } else {
        url = RouteUtils.setPathParamsObject(url, pathVariables);
      }
    }

    return url;
  }
}
