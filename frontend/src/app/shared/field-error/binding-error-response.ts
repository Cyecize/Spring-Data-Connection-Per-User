import {FieldError} from "./field-error";
import {ErrorResponse} from "../http/error-response";

export interface BindingErrorResponse extends ErrorResponse {
  fieldErrors: FieldError[];
}
