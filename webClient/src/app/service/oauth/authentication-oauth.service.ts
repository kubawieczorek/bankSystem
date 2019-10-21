import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';

export class Foo {
  constructor(
    public id: number,
    public name: string) {
  }
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationOauthService {

  constructor(
    private router: Router, private http: HttpClient, private cookie: CookieService) {
  }

  obtainAccessToken(username, password) {
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    params.append('grant_type', 'password');
    params.append('client_id', 'clientId');
    const headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      Authorization: 'Basic ' + btoa('clientId:clientSecret')
    });

    return this.http.post('http://localhost:8080/oauth/token',
      params.toString(), {headers}).pipe(
      map(
        res => {
          this.saveToken(res)
          return res;
        }
      )
    );
  }

  saveToken(token) {
    const expireDate = new Date().getTime() + (1000 * token.expires_in);
    this.cookie.set('access_token', token.access_token, expireDate);
    this.router.navigate(['/']);
  }

  getResource(resourceUrl): Observable<object> {
    const headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      Authorization: 'Bearer ' + this.cookie.get('access_token')
    });
    return this.http.get(resourceUrl, {headers}).pipe(
      map(
        res => {
          return res;
        }
      ));
    // .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  checkCredentials() {
    if (!this.cookie.check('access_token')) {
      this.router.navigate(['/login']);
    }
  }

  isUserLoggedIn() {
    const cookie = this.cookie.get('access_token');
    console.log(!(cookie === ''));
    return !(cookie === '');
  }

  logOut() {
    this.cookie.delete('access_token');
    this.router.navigate(['/']);
  }
}
