import {Component, OnInit} from '@angular/core';
import {TaskCreate} from "../../core/task/task-create";
import {FieldError} from "../../shared/field-error/field-error";
import {TaskService} from "../../core/task/task.service";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {

  constructor(private taskService: TaskService) {
  }

  errors: FieldError[] = [];

  ngOnInit(): void {

  }

  async onCreateTask(data: TaskCreate) {
    this.errors = [];
    this.errors = await this.taskService.createTask(data);
  }
}
