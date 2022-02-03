import {NgModule} from '@angular/core';
import {ErrorMessageComponent} from './error-message/error-message.component';
import {FilterFieldErrorPipe} from './filter-field-error.pipe';
import {CommonModule} from '@angular/common';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    ErrorMessageComponent
  ],
  declarations: [ErrorMessageComponent, FilterFieldErrorPipe]
})
export class FieldErrorModule {

}
