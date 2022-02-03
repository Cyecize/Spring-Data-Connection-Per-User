import { Component, OnInit } from '@angular/core';
import {AppRoutingPath} from "../../app-routing.path";

@Component({
  selector: 'app-database-connect',
  templateUrl: './database-connect.component.html',
  styleUrls: ['./database-connect.component.scss']
})
export class DatabaseConnectComponent implements OnInit {

  constructor() { }

  routes = AppRoutingPath;

  ngOnInit(): void {
  }

}
