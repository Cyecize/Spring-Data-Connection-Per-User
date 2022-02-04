import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ObjectUtils} from '../util/object-utils';
import {HttpOptions} from './http-options';
import {HttpHeader} from './http-header';
import {environment} from '../../../environments/environment';

@Injectable({providedIn: 'root'})
export class HttpClientService {

  constructor(protected httpClient: HttpClient) {

  }

  public get<TResponse>(url: string, options = {}): Observable<TResponse> {
    return this.httpClient.get<TResponse>(
      this.getFullUrl(url),
      ObjectUtils.merge<HttpOptions>([options, {headers: this.getHeaders()}])
    );
  }

  public post<TData, TResponse>(url: string, data: TData, options = {}): Observable<TResponse> {
    return this.httpClient.post<TResponse>(
      this.getFullUrl(url),
      data,
      ObjectUtils.merge<HttpOptions>([options, {headers: this.getHeaders()}])
    );
  }

  public put<TData, TResponse>(url: string, data: TData, options = {}): Observable<TResponse> {
    return this.httpClient.put<TResponse>(
      this.getFullUrl(url),
      data,
      ObjectUtils.merge<HttpOptions>([options, {headers: this.getHeaders()}])
    );
  }

  public patch<TData, TResponse>(url: string, data: TData, options = {}): Observable<TResponse> {
    return this.httpClient.patch<TResponse>(
      this.getFullUrl(url),
      data,
      ObjectUtils.merge<HttpOptions>([options, {headers: this.getHeaders()}])
    );
  }

  public delete<TResponse>(url: string, options = {}): Observable<TResponse> {
    return this.httpClient.delete<TResponse>(
      this.getFullUrl(url),
      ObjectUtils.merge<HttpOptions>([options, {headers: this.getHeaders()}])
    );
  }

  protected getHeaders(): HttpHeader {
    return {};
  }

  private getFullUrl(url: string): string {
    return environment.backendUrl + url;
  }
}
