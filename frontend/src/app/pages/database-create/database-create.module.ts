import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule, Routes} from "@angular/router";
import {DatabaseCreateComponent} from "./database-create.component";

const routes: Routes = [
  {
    path: '',
    component: DatabaseCreateComponent
  }
];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  declarations: [DatabaseCreateComponent],
  exports: [DatabaseCreateComponent]
})
export class DatabaseCreateModule {

}
