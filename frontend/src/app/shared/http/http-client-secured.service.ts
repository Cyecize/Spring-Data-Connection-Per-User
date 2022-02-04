import {Injectable} from '@angular/core';
import {HttpClientService} from './http-client.service';
import {HttpHeader, HttpHeaderType} from './http-header';
import {HttpClient} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';

@Injectable({providedIn: 'root'})
export class HttpClientSecuredService extends HttpClientService {

  constructor(httpClient: HttpClient,
              private cookieService: CookieService) {
    super(httpClient);
  }

  protected override getHeaders(): HttpHeader {
    const headers = super.getHeaders();

    const authToken = this.cookieService.get(HttpHeaderType.AUTHORIZATION_TOKEN);
    headers[HttpHeaderType.AUTHORIZATION_TOKEN] = authToken || '';

    return headers;
  }
}
