import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {TaskComponent} from "./task.component";
import { CreateTaskComponent } from './components/create-task/create-task.component';
import { TasksFilterComponent } from './components/tasks-filter/tasks-filter.component';
import { TasksGridComponent } from './components/tasks-grid/tasks-grid.component';
import {DpDatePickerModule} from "ng2-date-picker";
import {ModalModule} from "../../shared/modal/modal.module";

const routes: Routes = [
  {
    path: '',
    component: TaskComponent
  }
];

@NgModule({
    imports: [SharedModule, RouterModule.forChild(routes), DpDatePickerModule, ModalModule],
  declarations: [TaskComponent, CreateTaskComponent, TasksFilterComponent, TasksGridComponent],
  exports: [TaskComponent]
})
export class TaskModule {

}
