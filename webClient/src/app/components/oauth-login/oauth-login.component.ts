import {Component, OnInit} from '@angular/core';
import {AuthenticationOauthService} from '../../service/oauth/authentication-oauth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-oauth-login',
  templateUrl: './oauth-login.component.html',
  styleUrls: ['./oauth-login.component.css']
})
export class OauthLoginComponent implements OnInit {
  username = '';
  password = '';
  invalidLogin = false;

  constructor(private router: Router,
              private service: AuthenticationOauthService) {
  }

  checkLogin() {
    this.service.obtainAccessToken(this.username, this.password)
      .subscribe(
        data => {
          this.router.navigate(['']);
          this.invalidLogin = false;
        },
        error => {
          this.invalidLogin = true;
        }
      );
  }

  ngOnInit(): void {
  }

}
