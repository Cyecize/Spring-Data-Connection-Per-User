import { Component, OnInit } from '@angular/core';
import {AppRoutingPath} from "../../../app-routing.path";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor() { }

  routes = AppRoutingPath;

  ngOnInit(): void {
  }

}
