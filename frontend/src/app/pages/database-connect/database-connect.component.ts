import {Component, OnInit} from '@angular/core';
import {DatabaseProviderService} from "../../core/database/database-provider.service";
import {DatabaseProviderModel} from "../../core/database/database-provider.model";
import {DatabaseConnectModel} from "../../core/database/database-connect.model";
import {FieldError} from "../../shared/field-error/field-error";
import {DatabaseConnectService} from "../../core/database/database-connect.service";
import {RouteNavigator} from "../../core/routing/route-navigator.service";
import {AppRoutingPath} from "../../app-routing.path";

@Component({
  selector: 'app-database-connect',
  templateUrl: './database-connect.component.html',
  styleUrls: ['./database-connect.component.scss']
})
export class DatabaseConnectComponent implements OnInit {

  providers!: DatabaseProviderModel[];

  fieldErrors: FieldError[] = [];

  hasDbConnection!: boolean;

  constructor(private providerService: DatabaseProviderService,
              private databaseConnectService: DatabaseConnectService,
              private nav: RouteNavigator) {
  }

  ngOnInit(): void {
    this.providerService.getProviders().subscribe(value => this.providers = value);
    this.databaseConnectService.hasEstablishedConnection().subscribe(value => this.hasDbConnection = value);
  }

  async onFormSubmit(databaseConnectModel: DatabaseConnectModel) {
    this.fieldErrors = [];
    this.fieldErrors = await this.databaseConnectService.connectToDatabase(databaseConnectModel);
    if (this.fieldErrors.length === 0) {
      this.nav.navigate(AppRoutingPath.DATABASE_SELECT);
    }
  }
}
