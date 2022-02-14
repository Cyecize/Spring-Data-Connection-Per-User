import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared/shared.module";
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login.component";

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  }
];


@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  declarations: [LoginComponent],
  exports: [LoginComponent]
})
export class LoginModule {

}
