import {Injectable} from "@angular/core";
import {BehaviorSubject, catchError, firstValueFrom, map, Observable, of} from "rxjs";
import {UserModel} from "./user.model";
import {UserRepository} from "./user.repository";
import {FieldError} from "../../shared/field-error/field-error";
import {HttpErrorResponse} from "@angular/common/http";
import {HttpStatus} from "../../shared/http/http-status";
import {BindingErrorResponse} from "../../shared/field-error/binding-error-response";
import {ObjectUtils} from "../../shared/util/object-utils";

@Injectable({providedIn: 'root'})
export class UserService {

  private readonly currentUser = new BehaviorSubject<UserModel | null>(null);
  public readonly currentUser$ = this.currentUser.asObservable();

  constructor(private repository: UserRepository) {
    this.refreshCurrentUser();
  }

  public async refreshCurrentUser(): Promise<void> {
    try {
      const user: UserModel = await firstValueFrom(this.repository.fetch());
      this.currentUser.next(user);
    } catch (error) {
      this.currentUser.next(null);
    }
  }

  public async login(data: { username: string, password: String }): Promise<FieldError[]> {
    try {
      await firstValueFrom(this.repository.login(data));
      return [];
    } catch (error: HttpErrorResponse | any) {
      if (error.status === HttpStatus.NOT_ACCEPTABLE) {
        return (error.error as BindingErrorResponse).fieldErrors;
      } else {
        return [{
          message: error.error.message,
          field: ''
        }];
      }
    } finally {
      await this.refreshCurrentUser();
    }
  }

  public logout(): Observable<any> {
    if (ObjectUtils.isNil(this.currentUser.value)) {
      return new Observable<any>();
    }

    return this.repository.logout().pipe(map(value => {
      this.currentUser.next(null);
      return value;
    }));
  }

  public isLoggedIn(): Observable<boolean> {
    return this.repository.fetch().pipe(map(value => true), catchError(err => of(false)));
  }
}
