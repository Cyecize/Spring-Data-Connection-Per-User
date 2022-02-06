import {EventEmitter, Injectable} from "@angular/core";
import {DatabaseConnectRepository} from "./database-connect.repository";
import {firstValueFrom, Observable} from "rxjs";
import {ObjectUtils} from "../../shared/util/object-utils";
import {DatabaseConnectModel} from "./database-connect.model";
import {FieldError} from "../../shared/field-error/field-error";
import {BindingErrorResponse} from "../../shared/field-error/binding-error-response";
import {HttpStatus} from "../../shared/http/http-status";
import {HttpErrorResponse} from "@angular/common/http";
import {DatabaseCreateModel} from "./database-create.model";

@Injectable({providedIn: 'root'})
export class DatabaseConnectService {

  private establishedConnection!: boolean;
  private readonly hasEstablishedConnectionEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  private selectedDatabase!: string;
  private readonly selectedDatabaseEvent: EventEmitter<string> = new EventEmitter<string>();

  constructor(private repository: DatabaseConnectRepository) {
    this.refreshConnectionStatus();
  }

  public hasEstablishedConnection(): Observable<boolean> {
    if (!ObjectUtils.isNil(this.establishedConnection)) {
      return new Observable<boolean>(subscriber => subscriber.next(this.establishedConnection));
    }

    return this.hasEstablishedConnectionEvent;
  }

  public getSelectedDatabase(): Observable<string> {
    if (!ObjectUtils.isNil(this.selectedDatabase)) {
      return new Observable<string>(subscriber => subscriber.next(this.selectedDatabase));
    }

    return this.selectedDatabaseEvent;
  }

  public refreshConnectionStatus(): void {
    this.repository.hasEstablishedConnection().subscribe(value => {
      this.establishedConnection = value;
      this.hasEstablishedConnectionEvent.emit(value);
      this.refreshSelectedDatabase();
    });
  }

  public refreshSelectedDatabase(): void {
    this.repository.fetchSelectedDatabase().subscribe(value => {
      this.selectedDatabase = value.database;
      this.selectedDatabaseEvent.emit(value.database);
    })
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
          message: error.error.message,
          field: ''
        }];
      }
    } finally {
      this.refreshConnectionStatus();
    }
  }

  public async selectDatabase(selectedDatabase: { selectedDatabase: string }): Promise<FieldError[]> {
    try {
      await firstValueFrom(this.repository.selectDatabase(selectedDatabase));
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
      this.refreshConnectionStatus();
    }
  }

  public getAllDatabases(): Observable<string[]> {
    return this.repository.fetchDatabases();
  }

  async createDatabase(data: DatabaseCreateModel): Promise<FieldError[]> {
    try {
      await firstValueFrom(this.repository.createDatabase(data));
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
      this.refreshConnectionStatus();
    }
  }
}
