import {NgModule} from '@angular/core';
import {NotFoundComponent} from './not-found.component';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../../shared/shared.module';

const routes: Routes = [
  {
    path: '',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ],
  declarations: [NotFoundComponent]
})
export class NotFoundModule {

}
