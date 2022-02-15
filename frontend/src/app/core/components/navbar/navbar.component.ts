import {Component, OnInit} from '@angular/core';
import {AppRoutingPath} from "../../../app-routing.path";
import {DatabaseConnectService} from "../../database/database-connect.service";
import {UserService} from "../../user/user.service";
import {UserModel} from "../../user/user.model";
import {RouteNavigator} from "../../routing/route-navigator.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private databaseService: DatabaseConnectService,
              private userService: UserService,
              private nav: RouteNavigator) {
  }

  routes = AppRoutingPath;

  hasDbConnection!: boolean;

  selectedDatabase!: string;

  currentUser: UserModel | null = null;

  ngOnInit(): void {
    this.databaseService.hasEstablishedConnection().subscribe(value => this.hasDbConnection = value);
    this.databaseService.getSelectedDatabase().subscribe(value => this.selectedDatabase = value);
    this.userService.currentUser$.subscribe(value => this.currentUser = value);
  }

  logout() {
    this.userService.logout().subscribe(res => {
      this.nav.navigate(AppRoutingPath.LOGIN);
    });
  }

  getRoles(currentUser: UserModel): string {
    return currentUser.roles.map(r => r.authority).join(", ");
  }
}
