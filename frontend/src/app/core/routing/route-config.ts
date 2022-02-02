export class RouteConfig {
  // tslint:disable-next-line:variable-name
  private readonly _path: string;
  private readonly parent: RouteConfig | null;

  constructor(path: string, parent: RouteConfig | null = null) {
    this._path = path;
    this.parent = parent;
  }

  public get absolutePath(): string {
    if (this.parent !== null) {
      return this.parent.absolutePath + '/' + this.path;
    }

    return '/' + this.path;
  }

  public get path(): string {
    return this._path;
  }

  public toString(): string {
    return this.absolutePath;
  }
}
