import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientListComponent } from './components/client-list/client-list.component';
import {ClientService} from './service/client.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BasicAuthHttpInterceptorService} from './service/basic-auth-http-interceptor.service';
import { AccountsComponent } from './components/account/accounts.component';
import { OauthLoginComponent } from './components/oauth-login/oauth-login.component';
import { CookieService } from 'ngx-cookie-service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatNativeDateModule} from '@angular/material/core';
import {MatCardModule, MatIconModule, MatListModule, MatTableModule} from '@angular/material';
import { TransferComponent } from './components/transfer/transfer.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material';
import { BasicLoginComponent } from './components/basic-login/basic-login.component';

@NgModule({
  declarations: [
    AppComponent,
    ClientListComponent,
    LoginComponent,
    AccountsComponent,
    OauthLoginComponent,
    TransferComponent,
    BasicLoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatListModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatCardModule,
    ReactiveFormsModule
  ],
  providers: [ClientService, CookieService, { provide: HTTP_INTERCEPTORS, useClass: BasicAuthHttpInterceptorService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
