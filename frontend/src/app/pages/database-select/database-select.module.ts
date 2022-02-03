import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule, Routes} from "@angular/router";
import {DatabaseSelectComponent} from "./database-select.component";

const routes: Routes = [
  {
    path: '',
    component: DatabaseSelectComponent
  }
];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  declarations: [DatabaseSelectComponent],
  exports: [DatabaseSelectComponent]
})
export class DatabaseSelectModule {

}
