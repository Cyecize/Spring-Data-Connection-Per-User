import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SharedModule} from "./shared/shared.module";
import {NavbarModule} from "./core/components/navbar/navbar.module";
import {CookieService} from "ngx-cookie-service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {HttpRetryInterceptor} from "./shared/http/interceptors/http-retry.interceptor";
import {HttpRedirectInterceptor} from "./shared/http/interceptors/http-redirect.interceptor";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    NavbarModule,
    HttpClientModule,
  ],
  providers: [
    CookieService,
    {provide: HTTP_INTERCEPTORS, useClass: HttpRetryInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: HttpRedirectInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
