import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {HomeComponent} from "./home.component";
import {RouterModule, Routes} from "@angular/router";
import {TaskModule} from "../task/task.module";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  }
];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes), TaskModule],
  declarations: [HomeComponent],
  exports: [HomeComponent]
})
export class HomeModule {

}
