import { TestBed } from '@angular/core/testing';

import { AuthenticationOauthService } from './authentication-oauth.service';

describe('AuthenticationOauthService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AuthenticationOauthService = TestBed.get(AuthenticationOauthService);
    expect(service).toBeTruthy();
  });
});
