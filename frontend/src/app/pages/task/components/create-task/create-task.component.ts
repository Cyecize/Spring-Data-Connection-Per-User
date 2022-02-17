import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import dateFormat from "dateformat";
import {DatePickerComponent} from "ng2-date-picker";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TaskCreate} from "../../../../core/task/task-create";
import {GeneralConstants} from "../../../../shared/constants/general-constants";

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.scss']
})
export class CreateTaskComponent implements OnInit {

  datePickerConfig: any;

  @ViewChild('datePicker')
  datePicker!: DatePickerComponent;

  form!: FormGroup;

  @Output()
  formSubmitted: EventEmitter<TaskCreate> = new EventEmitter<TaskCreate>();

  constructor(private fb: FormBuilder) {
    this.setDatePickerConfig();
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      'descr': ['', [Validators.required, Validators.maxLength(255)]]
    });
  }

  private setDatePickerConfig() {
    this.datePickerConfig = {
      format: "YYYY-MM-DD, HH:mm",
      showSeconds: false,
      showTwentyFourHours: true,
      min: dateFormat(new Date(), "yyyy-mm-dd HH:MM"),
      disableKeypress: true
    };
  }

  openDatePicker() {
    this.datePicker.api.open();
  }

  onFormSubmit() {
    let dueDate: string | undefined;
    const selectedDates = this.datePicker.selected;
    if (selectedDates.length > 0) {
      dueDate = dateFormat(selectedDates[0].toDate(), GeneralConstants.DATE_TIME_FORMAT)
    }

    this.formSubmitted.emit({
      dueDate,
      description: this.form.value.descr
    });

    this.form.reset();
  }
}
