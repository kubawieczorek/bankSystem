import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthenticationService {

  constructor(
    private httpClient: HttpClient, private router: Router
  ) {
  }

  authenticate(username, password) {
    console.log(username);
    console.log(password);
    const headers = new HttpHeaders({Authorization: 'Basic ' + btoa(username + ':' + password)});
    return this.httpClient.get('http://localhost:8080/clients', {headers}).pipe(
      map(
        userData => {
          console.log(userData);
          sessionStorage.setItem('username', username);
          const authString = 'Basic ' + btoa(username + ':' + password);
          sessionStorage.setItem('basicauth', authString);
          return userData;
        }
      )
    );
  }

  isUserLoggedIn() {
    const user = sessionStorage.getItem('username');
    console.log(!(user === null));
    return !(user === null);
  }

  logOut() {
    this.httpClient.post('http://localhost:8080/logout', {});
    sessionStorage.removeItem('username');
    this.router.navigate(['/']);
  }

}
