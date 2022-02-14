import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Lengths} from "../../shared/constants/lengths";
import {FieldError} from "../../shared/field-error/field-error";
import {UserService} from "../../core/user/user.service";
import {RouteNavigator} from "../../core/routing/route-navigator.service";
import {AppRoutingPath} from "../../app-routing.path";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private nav: RouteNavigator) {

  }

  form!: FormGroup;

  errors: FieldError[] = [];

  ngOnInit(): void {
    this.form = this.fb.group({
      'username': ['', [Validators.required, Validators.maxLength(Lengths.MAX_VARCHAR)]],
      'password': ['', [Validators.maxLength(Lengths.MAX_VARCHAR)]],
    });
  }

  async onFormSubmit() {
    this.errors = [];
    this.errors = await this.userService.login(this.form.value);

    if (this.errors.length === 0) {
      this.nav.navigate(AppRoutingPath.HOME);
    }
  }

}
