import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Page} from "../../../../shared/util/page";
import {TaskModel} from "../../../../core/task/task.model";

@Component({
  selector: 'app-tasks-grid',
  templateUrl: './tasks-grid.component.html',
  styleUrls: ['./tasks-grid.component.scss']
})
export class TasksGridComponent implements OnInit {

  constructor() {
  }

  @Input()
  tasks!: Page<TaskModel>;

  @Output()
  pageChanged: EventEmitter<number> = new EventEmitter<number>();

  @Output()
  onTaskDelete: EventEmitter<TaskModel> = new EventEmitter<TaskModel>();

  @Output()
  onTaskStatusChange = new EventEmitter<{ task: TaskModel; inProgress: boolean }>();

  ngOnInit(): void {
  }

  numSequence(n: number): Array<number> {
    return Array(n);
  }

  changePage(page: number) {
    this.pageChanged.next(page);
  }

  deleteTask(task: TaskModel) {
    this.onTaskDelete.next(task);
  }

  onStatusChange(task: TaskModel, event: any) {
    this.onTaskStatusChange.next({
      task: task,
      inProgress: !task.inProgress
    })

    event.preventDefault();
  }
}
