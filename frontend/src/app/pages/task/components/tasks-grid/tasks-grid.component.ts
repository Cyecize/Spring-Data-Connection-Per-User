import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Page} from "../../../../shared/util/page";
import {TaskModel} from "../../../../core/task/task.model";

@Component({
  selector: 'app-tasks-grid',
  templateUrl: './tasks-grid.component.html',
  styleUrls: ['./tasks-grid.component.scss']
})
export class TasksGridComponent implements OnInit {

  constructor() { }

  @Input()
  tasks!: Page<TaskModel>;

  @Output()
  pageChanged: EventEmitter<number> = new EventEmitter<number>();

  ngOnInit(): void {
  }

  numSequence(n: number): Array<number> {
    return Array(n);
  }

  changePage(page: number) {
    this.pageChanged.next(page);
  }
}
