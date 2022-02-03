import { Component, OnInit } from '@angular/core';
import {AppRoutingPath} from "../../app-routing.path";

@Component({
  selector: 'app-database-create',
  templateUrl: './database-create.component.html',
  styleUrls: ['./database-create.component.scss']
})
export class DatabaseCreateComponent implements OnInit {

  constructor() { }

  routes = AppRoutingPath;

  ngOnInit(): void {
  }

}
