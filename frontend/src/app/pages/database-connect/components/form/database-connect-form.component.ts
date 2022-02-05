import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AppRoutingPath} from "../../../../app-routing.path";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FieldError} from "../../../../shared/field-error/field-error";
import {DatabaseConnectModel} from "../../../../core/database/database-connect.model";
import {Lengths} from "../../../../shared/constants/lengths";
import {DatabaseProviderModel} from "../../../../core/database/database-provider.model";
import {ObjectUtils} from "../../../../shared/util/object-utils";

@Component({
  selector: 'app-database-connect-form',
  templateUrl: './database-connect-form.component.html',
  styleUrls: ['./database-connect-form.component.scss']
})
export class DatabaseConnectFormComponent implements OnInit {

  constructor(private fb: FormBuilder) {
  }

  _selectedProvider: string = '';

  port!: number;

  routes = AppRoutingPath;

  @Input()
  hasDbConnection!: boolean;

  @Input()
  errors!: FieldError[];

  @Input()
  dbProviders!: DatabaseProviderModel[];

  @Output()
  formSubmit: EventEmitter<DatabaseConnectModel> = new EventEmitter<DatabaseConnectModel>();

  form!: FormGroup;

  get selectedProvider() {
    return this._selectedProvider;
  }

  set selectedProvider(value: string) {
    this._selectedProvider = value;

    if (ObjectUtils.isNil(value) || ObjectUtils.isNil(this.dbProviders)) {
      return;
    }

    this.dbProviders.filter(provider => provider.name === value).forEach(provider => this.port = provider.defaultPort);
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      'databaseProvider': ['', [Validators.required, Validators.maxLength(Lengths.MAX_NAME_LENGTH)]],
      'host': ['', [Validators.required, Validators.maxLength(Lengths.MAX_VARCHAR)]],
      'port': ['', [Validators.required]],
      'username': ['', [Validators.required, Validators.maxLength(Lengths.MAX_VARCHAR)]],
      'password': ['', [Validators.maxLength(Lengths.MAX_VARCHAR)]],
      'useSSL': [false, [Validators.required]],
    });
  }

  onFormSubmit() {
    this.formSubmit?.emit(this.form.value);
  }
}
