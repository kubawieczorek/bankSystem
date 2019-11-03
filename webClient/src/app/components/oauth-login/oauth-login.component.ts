import {Component, OnInit} from '@angular/core';
import {AuthenticationOauthService} from '../../service/oauth/authentication-oauth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-oauth-login',
  templateUrl: './oauth-login.component.html',
  styleUrls: ['./oauth-login.component.css']
})
export class OauthLoginComponent implements OnInit {
  ngOnInit(): void {
  }
}
