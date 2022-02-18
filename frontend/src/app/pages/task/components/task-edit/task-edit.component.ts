import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {TaskModel} from "../../../../core/task/task.model";
import {DatePickerComponent} from "ng2-date-picker";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TaskCreate} from "../../../../core/task/task-create";
import dateFormat from "dateformat";
import {GeneralConstants} from "../../../../shared/constants/general-constants";
import {ObjectUtils} from "../../../../shared/util/object-utils";

@Component({
  selector: 'app-task-edit',
  templateUrl: './task-edit.component.html',
  styleUrls: ['./task-edit.component.scss']
})
export class TaskEditComponent implements OnInit {

  private _task!: TaskModel;

  datePickerConfig: any;

  @ViewChild('datePicker')
  datePicker!: DatePickerComponent;

  form!: FormGroup;

  @Output()
  formSubmitted: EventEmitter<TaskCreate> = new EventEmitter<TaskCreate>();

  completedTask!: boolean;

  @Input()
  set task(value: TaskModel) {
    this._task = value;

    if (!ObjectUtils.isNil(value)) {
      this.completedTask = !value.inProgress;
    }
  }

  get task() {
    return this._task;
  }

  constructor(private fb: FormBuilder) {
    this.setDatePickerConfig();
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      'descr': ['', [Validators.required, Validators.maxLength(255)]],
      'completed': [''],
    });
  }

  private setDatePickerConfig() {
    this.datePickerConfig = {
      format: "YYYY-MM-DD, HH:mm",
      showSeconds: false,
      showTwentyFourHours: true,
      min: dateFormat(new Date(), "yyyy-mm-dd HH:MM"),
      disableKeypress: false
    };
  }

  openDatePicker() {
    this.datePicker.api.open();
  }

  onFormSubmit() {
    let dueDate: string | undefined = this.task.dueDate;

    const selectedDates = this.datePicker.selected;

    if (selectedDates.length > 0) {
      dueDate = dateFormat(selectedDates[0].toDate(), GeneralConstants.DATE_TIME_FORMAT)
    }

    this.formSubmitted.emit({
      dueDate,
      description: this.form.value.descr,
      inProgress: this.form.value.completed != true
    });

    this.datePicker.api.moveCalendarTo('');
  }
}
