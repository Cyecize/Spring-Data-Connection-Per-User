import {NgModule} from '@angular/core';
import {ErrorMessageComponent} from './error-message/error-message.component';
import {FilterFieldErrorPipe} from './filter-field-error.pipe';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '../../core/translate/translate.module';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule
  ],
  exports: [
    ErrorMessageComponent
  ],
  declarations: [ErrorMessageComponent, FilterFieldErrorPipe]
})
export class FieldErrorModule {

}
