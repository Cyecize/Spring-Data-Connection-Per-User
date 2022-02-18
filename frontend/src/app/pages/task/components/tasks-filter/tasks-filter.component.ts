import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TaskQuery} from "../../../../core/task/task.query";

enum Direction {
  ASC = 'ASC',
  DESC = 'DESC'
}

@Component({
  selector: 'app-tasks-filter',
  templateUrl: './tasks-filter.component.html',
  styleUrls: ['./tasks-filter.component.scss']
})
export class TasksFilterComponent implements OnInit {

  readonly orderOptions: { name: string, property: string }[] = [
    {
      name: 'Date Added', property: 'createDate'
    },
    {
      name: 'Due Date', property: 'dueDate'
    }
  ];

  constructor(private fb: FormBuilder) {
  }

  form!: FormGroup;

  directions = Direction;

  currentDirection = Direction.DESC;

  @Output()
  filtersChanged: EventEmitter<TaskQuery> = new EventEmitter<TaskQuery>();

  ngOnInit(): void {
    this.form = this.fb.group({
      'size': [5, Validators.required],
      'orderBy': [this.orderOptions[0].property, Validators.required],
      'direction': [Direction.DESC, Validators.required],
      'hasDueDate': [''],
      'inProgress': [''],
      'description': ['']
    });

    this.form.valueChanges.subscribe(value => {
      this.filtersChanged.next(value);
    });
  }
}
