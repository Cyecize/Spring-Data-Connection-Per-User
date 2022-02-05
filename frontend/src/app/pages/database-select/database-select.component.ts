import {Component, OnInit} from '@angular/core';
import {AppRoutingPath} from "../../app-routing.path";
import {DatabaseConnectService} from "../../core/database/database-connect.service";

@Component({
  selector: 'app-database-select',
  templateUrl: './database-select.component.html',
  styleUrls: ['./database-select.component.scss']
})
export class DatabaseSelectComponent implements OnInit {

  constructor(private databaseService: DatabaseConnectService) {

  }

  routes = AppRoutingPath;

  databases: string[] = [];

  ngOnInit(): void {
    this.loadDatabases();
  }

  private loadDatabases(): void {
    this.databaseService.getAllDatabases().subscribe(value => this.databases = value);
  }
}
