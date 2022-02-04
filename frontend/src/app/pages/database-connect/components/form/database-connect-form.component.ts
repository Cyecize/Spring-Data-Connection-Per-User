import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AppRoutingPath} from "../../../../app-routing.path";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FieldError} from "../../../../shared/field-error/field-error";
import {DatabaseConnectModel} from "../../../../core/database/database-connect.model";
import {Lengths} from "../../../../shared/constants/lengths";

@Component({
  selector: 'app-database-connect-form',
  templateUrl: './database-connect-form.component.html',
  styleUrls: ['./database-connect-form.component.scss']
})
export class DatabaseConnectFormComponent implements OnInit {

  constructor(private fb: FormBuilder) {
  }

  routes = AppRoutingPath;

  @Input()
  hasDbConnection!: boolean;

  @Input()
  errors!: FieldError[];

  @Output()
  formSubmit: EventEmitter<DatabaseConnectModel> = new EventEmitter<DatabaseConnectModel>();

  form!: FormGroup;

  ngOnInit(): void {
    this.form = this.fb.group({
      'databaseProvider': ['', [Validators.required, Validators.maxLength(Lengths.MAX_NAME_LENGTH)]],
      'host': ['', [Validators.required, Validators.maxLength(Lengths.MAX_VARCHAR)]],
      'port': ['', [Validators.required]],
      'username': ['', [Validators.required, Validators.maxLength(Lengths.MAX_VARCHAR)]],
      'password': ['', [Validators.maxLength(Lengths.MAX_VARCHAR)]]
    });
  }

  onFormSubmit() {
    this.formSubmit?.emit(this.form.value);
    console.log(this.form.value);
  }
}
