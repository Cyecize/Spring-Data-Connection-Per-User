import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {DatabaseConnectComponent} from "./database-connect.component";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {
    path: '',
    component: DatabaseConnectComponent
  }
];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  declarations: [DatabaseConnectComponent],
  exports: [DatabaseConnectComponent]
})
export class DatabaseConnectModule {

}
