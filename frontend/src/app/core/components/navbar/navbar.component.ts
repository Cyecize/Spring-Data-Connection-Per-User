import { Component, OnInit } from '@angular/core';
import {AppRoutingPath} from "../../../app-routing.path";
import {DatabaseConnectService} from "../../database/database-connect.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private databaseService: DatabaseConnectService) { }

  routes = AppRoutingPath;

  hasDbConnection!: boolean;

  selectedDatabase!: string;

  ngOnInit(): void {
    this.databaseService.hasEstablishedConnection().subscribe(value => this.hasDbConnection = value);
    this.databaseService.getSelectedDatabase().subscribe(value => this.selectedDatabase = value);
  }

}
