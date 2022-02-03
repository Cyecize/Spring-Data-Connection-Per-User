import { Component, OnInit } from '@angular/core';
import {AppRoutingPath} from "../../app-routing.path";

@Component({
  selector: 'app-database-select',
  templateUrl: './database-select.component.html',
  styleUrls: ['./database-select.component.scss']
})
export class DatabaseSelectComponent implements OnInit {

  constructor() { }

  routes = AppRoutingPath;

  ngOnInit(): void {
  }

}
