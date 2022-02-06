import {Component, OnInit} from '@angular/core';
import {AppRoutingPath} from "../../app-routing.path";
import {DatabaseConnectService} from "../../core/database/database-connect.service";
import {FieldError} from "../../shared/field-error/field-error";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-database-select',
  templateUrl: './database-select.component.html',
  styleUrls: ['./database-select.component.scss']
})
export class DatabaseSelectComponent implements OnInit {

  constructor(private databaseService: DatabaseConnectService,
              private fb: FormBuilder) {

  }

  routes = AppRoutingPath;

  databases: string[] = [];

  errors: FieldError[] = [];

  form!: FormGroup;

  ngOnInit(): void {
    this.loadDatabases();
    this.form = this.fb.group({
      'selectedDatabase': ['', [Validators.required]]
    })
  }

  private loadDatabases(): void {
    this.databaseService.getAllDatabases().subscribe(value => this.databases = value);
  }

  async onFormSubmit() {
    this.errors = [];
    this.errors = await this.databaseService.selectDatabase(this.form.value);
  }
}
