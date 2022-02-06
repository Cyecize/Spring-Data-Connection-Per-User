import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule, Routes} from "@angular/router";
import {DatabaseCreateComponent} from "./database-create.component";
import { DatabaseCreateFormComponent } from './components/database-create-form/database-create-form.component';

const routes: Routes = [
  {
    path: '',
    component: DatabaseCreateComponent
  }
];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  declarations: [DatabaseCreateComponent, DatabaseCreateFormComponent],
  exports: [DatabaseCreateComponent]
})
export class DatabaseCreateModule {

}
