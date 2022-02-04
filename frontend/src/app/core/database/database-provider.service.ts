import {EventEmitter, Injectable} from "@angular/core";
import {DatabaseProviderModel} from "./database-provider.model";
import {DatabaseProviderRepository} from "./database-provider.repository";
import {Observable} from "rxjs";
import {ObjectUtils} from "../../shared/util/object-utils";

@Injectable({providedIn: 'root'})
export class DatabaseProviderService {

  private providers?: DatabaseProviderModel[];
  private readonly providersEvent: EventEmitter<DatabaseProviderModel[]> = new EventEmitter<DatabaseProviderModel[]>();

  constructor(private repository: DatabaseProviderRepository) {
    this.repository.fetch().subscribe(value => {
      this.providers = value;
      this.providersEvent.emit(value);
    })
  }

  public getProviders(): Observable<DatabaseProviderModel[]> {
    if (!ObjectUtils.isNil(this.providers)) {
      return new Observable<DatabaseProviderModel[]>(subscriber => subscriber.next(this.providers));
    }

    return this.providersEvent;
  }
}
