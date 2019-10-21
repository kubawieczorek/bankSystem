import {Injectable} from '@angular/core';
import {HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';

@Injectable()
export class BasicAuthHttpInterceptorService implements HttpInterceptor {

  constructor(private cookie: CookieService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    if (sessionStorage.getItem('username') && sessionStorage.getItem('basicauth')) {
      req = req.clone({
        setHeaders: {
          Authorization: sessionStorage.getItem('basicauth')
        }
      });
    } else if (this.cookie.get('access_token') !== '') {
      req = req.clone({
        setHeaders: {
          Authorization: 'Bearer ' + this.cookie.get('access_token')
        }
      });
    }
    return next.handle(req);
  }
}
