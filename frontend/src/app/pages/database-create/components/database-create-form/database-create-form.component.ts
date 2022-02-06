import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FieldError} from "../../../../shared/field-error/field-error";
import {DatabaseCreateModel} from "../../../../core/database/database-create.model";
import {AppRoutingPath} from "../../../../app-routing.path";

@Component({
  selector: 'app-database-create-form',
  templateUrl: './database-create-form.component.html',
  styleUrls: ['./database-create-form.component.scss']
})
export class DatabaseCreateFormComponent implements OnInit {

  constructor(private fb: FormBuilder) {
  }

  @Input()
  errors!: FieldError[];

  @Output()
  formSubmit: EventEmitter<DatabaseCreateModel> = new EventEmitter<DatabaseCreateModel>();

  form!: FormGroup;

  routes = AppRoutingPath;

  ngOnInit(): void {
    this.form = this.fb.group({
      'databaseName': ['', [Validators.required]],
      'adminUsername': ['', [Validators.required]],
      'adminPassword': ['', [Validators.required]],
      'passwordConfirm': ['', [Validators.required]],
    });
  }

  onFormSubmit(): void {
    this.formSubmit.emit(this.form.value);
  }

}
