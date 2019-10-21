import {Injectable} from '@angular/core';
import {BasicAuthenticationService} from './basicAuth/basic-authentication.service';
import {AuthenticationOauthService} from './oauth/authentication-oauth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private basicAuth: BasicAuthenticationService,
    private oauth: AuthenticationOauthService
  ) {
  }

  isUserLoggedIn() {
    return this.basicAuth.isUserLoggedIn() || this.oauth.isUserLoggedIn();
  }

  logOut() {
    if (this.basicAuth.isUserLoggedIn()) {
      this.basicAuth.logOut();
    }
    if (this.oauth.isUserLoggedIn()) {
      this.oauth.logOut();
    }
  }
}
