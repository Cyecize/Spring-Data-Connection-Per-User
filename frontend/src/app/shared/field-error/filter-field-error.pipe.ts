import {Pipe, PipeTransform} from '@angular/core';
import {FieldError} from './field-error';
import {ObjectUtils} from '../util/object-utils';

@Pipe({
  name: 'filterFieldError'
})
export class FilterFieldErrorPipe implements PipeTransform {
  transform(errors: FieldError[], fieldName: string): FieldError[] {
    if (ObjectUtils.isNil(errors)) {
      errors = [];
    }

    return errors.filter(e => e.propertyPath === fieldName);
  }
}
