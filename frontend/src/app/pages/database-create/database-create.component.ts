import {Component, OnInit} from '@angular/core';
import {AppRoutingPath} from "../../app-routing.path";
import {DatabaseCreateModel} from "../../core/database/database-create.model";
import {FieldError} from "../../shared/field-error/field-error";
import {DatabaseConnectService} from "../../core/database/database-connect.service";
import {RouteNavigator} from "../../core/routing/route-navigator.service";

@Component({
  selector: 'app-database-create',
  templateUrl: './database-create.component.html',
  styleUrls: ['./database-create.component.scss']
})
export class DatabaseCreateComponent implements OnInit {

  constructor(private databaseService: DatabaseConnectService,
              private nav: RouteNavigator) {
  }

  routes = AppRoutingPath;

  errors: FieldError[] = [];

  ngOnInit(): void {

  }

  async onFormSubmit(databaseCreateModel: DatabaseCreateModel) {
    this.errors = [];
    this.errors = await this.databaseService.createDatabase(databaseCreateModel);
    if (this.errors.length == 0) {
      this.nav.navigate(AppRoutingPath.LOGIN);
    }
  }
}
