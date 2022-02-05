import {EventEmitter, Injectable} from "@angular/core";
import {DatabaseConnectRepository} from "./database-connect.repository";
import {firstValueFrom, Observable} from "rxjs";
import {ObjectUtils} from "../../shared/util/object-utils";
import {DatabaseConnectModel} from "./database-connect.model";
import {FieldError} from "../../shared/field-error/field-error";
import {BindingErrorResponse} from "../../shared/field-error/binding-error-response";
import {HttpStatus} from "../../shared/http/http-status";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class DatabaseConnectService {

  private establishedConnection!: boolean;
  private readonly hasEstablishedConnectionEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(private repository: DatabaseConnectRepository) {
    this.refreshConnectionStatus();
  }

  public hasEstablishedConnection(): Observable<boolean> {
    if (!ObjectUtils.isNil(this.establishedConnection)) {
      return new Observable<boolean>(subscriber => subscriber.next(this.establishedConnection));
    }

    return this.hasEstablishedConnectionEvent;
  }

  public refreshConnectionStatus(): void {
    this.repository.hasEstablishedConnection().subscribe(value => {
      this.establishedConnection = value;
      this.hasEstablishedConnectionEvent.emit(value);
    });
  }

  public async connectToDatabase(model: DatabaseConnectModel): Promise<FieldError[]> {
    try {
      await firstValueFrom(this.repository.connect(model));
      return [];
    } catch (error: HttpErrorResponse | any) {
      if (error.status === HttpStatus.NOT_ACCEPTABLE) {
        return (error.error as BindingErrorResponse).fieldErrors;
      } else {
        return [{
          message: 'Something Went Wrong',
          field: ''
        }];
      }
    } finally {
      this.refreshConnectionStatus();
    }
  }
}
