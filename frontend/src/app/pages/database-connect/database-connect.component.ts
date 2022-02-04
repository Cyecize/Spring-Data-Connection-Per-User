import {Component, OnInit} from '@angular/core';
import {DatabaseProviderService} from "../../core/database/database-provider.service";
import {DatabaseProviderModel} from "../../core/database/database-provider.model";
import {DatabaseConnectModel} from "../../core/database/database-connect.model";
import {FieldError} from "../../shared/field-error/field-error";

@Component({
  selector: 'app-database-connect',
  templateUrl: './database-connect.component.html',
  styleUrls: ['./database-connect.component.scss']
})
export class DatabaseConnectComponent implements OnInit {

  providers!: DatabaseProviderModel[];

  fieldErrors: FieldError[] = [];

  hasDbConnection: boolean = true;

  constructor(private providerService: DatabaseProviderService) {

  }

  ngOnInit(): void {
    this.providerService.getProviders().subscribe(value => this.providers = value);
  }

  onFormSubmit(databaseConnectModel: DatabaseConnectModel) {
    console.log(databaseConnectModel);
  }
}
