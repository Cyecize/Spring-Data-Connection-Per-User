import {Component, OnInit} from '@angular/core';
import {TaskCreate} from "../../core/task/task-create";
import {FieldError} from "../../shared/field-error/field-error";
import {TaskService} from "../../core/task/task.service";
import {TaskQuery} from "../../core/task/task.query";
import {Page} from "../../shared/util/page";
import {TaskModel} from "../../core/task/task.model";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {

  constructor(private taskService: TaskService) {
  }

  errors: FieldError[] = [];

  taskQuery!: TaskQuery;

  tasks!: Page<TaskModel>;

  ngOnInit(): void {
    this.taskService.tasks$.subscribe(value => {
      this.tasks = value;
    });
  }

  async onCreateTask(data: TaskCreate) {
    this.errors = [];
    this.errors = await this.taskService.createTask(data);
    this.onFilterChange(this.taskQuery);
  }

  onFilterChange(query: TaskQuery) {
    this.taskQuery = query;
    this.taskQuery.page = 0;
    this.taskService.loadTasks(query);
  }

  onPageChange(page: number) {
    if (this.taskQuery.page === page) {
      return;
    }

    this.taskQuery.page = page;
    this.taskService.loadTasks(this.taskQuery);
  }
}
