import {Component, Input} from '@angular/core';
import {FieldError} from '../field-error';

@Component({
  selector: 'app-field-error',
  templateUrl: './error-message.component.html',
})
export class ErrorMessageComponent {

  // @ts-ignore
  @Input() errors: FieldError[];

  // @ts-ignore
  @Input() fieldName: string;

  constructor() {

  }
}
