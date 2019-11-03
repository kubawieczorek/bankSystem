import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {BasicAuthenticationService} from '../../service/basicAuth/basic-authentication.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationOauthService} from '../../service/oauth/authentication-oauth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Input() title: string;
  @Input() isBasic: boolean;
  form: FormGroup;
  invalidLogin = false;
  private formSubmitAttempt: boolean;

  constructor(private router: Router,
              private fb: FormBuilder,
              private basicLoginService: BasicAuthenticationService,
              private oauthLoginService: AuthenticationOauthService) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  isFieldInvalid(field: string) {
    return (
      (!this.form.get(field).valid && this.form.get(field).touched) ||
      (this.form.get(field).untouched && this.formSubmitAttempt)
    );
  }

  onSubmit() {
    if (this.form.valid) {
      if (this.isBasic) {
        this.checkLoginBasic(this.form.get('userName').value, this.form.get('password').value);
      } else {
        this.checkLoginOAuth(this.form.get('userName').value, this.form.get('password').value);
      }
    }
    this.formSubmitAttempt = true;
  }

  checkLoginBasic(username, password) {
    this.basicLoginService.authenticate(username, password).subscribe(
      data => {
        this.router.navigate(['/clients']);
        this.invalidLogin = false;
      },
      error => {
        this.invalidLogin = true;
      }
    );
  }

  checkLoginOAuth(username, password) {
    this.oauthLoginService.obtainAccessToken(username, password).subscribe(
      data => {
        this.router.navigate(['/clients']);
        this.invalidLogin = false;
      },
      error => {
        this.invalidLogin = true;
      }
    );
  }
}
