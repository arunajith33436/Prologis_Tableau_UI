import { BrowserModule } from '@angular/platform-browser';
import { NgModule , ErrorHandler} from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ExploreComponent } from './component/explore/explore.component';
import { DatePipe } from '@angular/common';
import { GlobalErrorHandler } from './errorhandler/global-error-handler';
import { HttpRequestInterceptor } from './interceptor/http-request.interceptor';
import { ViewComponent } from './component/view/view.component';
import { TableauModule } from 'ngx-tableau';
@NgModule({
  declarations: [
    AppComponent,
    ExploreComponent,
    ViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TableauModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers:  [
    [
      {
        provide: HTTP_INTERCEPTORS,
        useClass: HttpRequestInterceptor,
        multi: true
      }
    ],  
    DatePipe,
    {provide: ErrorHandler, useClass: GlobalErrorHandler}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
