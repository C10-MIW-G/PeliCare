import { Injectable } from '@angular/core';
import * as moment from 'moment';

const JWT_TOKEN = 'jwt_token';
const EXPIRES_AT = 'expires_at';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  setSession(authResult: any) {
    localStorage.setItem(JWT_TOKEN, authResult.jwt);
    
    const expiresAt = moment().add(authResult.expiresIn, 'second');
    const sessionDuration = 1000 * 60 * 24; //24 minutes - same as backend
    localStorage.setItem(
      EXPIRES_AT,
      JSON.stringify(expiresAt.valueOf() + sessionDuration)
    );
  }

  getToken(): string | null{
    return localStorage.getItem(JWT_TOKEN);
  }

  public isNotExpired() {
    return moment().isBefore(this.getExpiration());
  }

  isExpired() {
    return !this.isNotExpired();
  }

  getExpiration() {
    const expiration = localStorage.getItem(EXPIRES_AT);
    const expiresAt = JSON.parse(expiration!);
    return moment(expiresAt);
  }

  removeToken() {
    if (localStorage.getItem(JWT_TOKEN)) {
      localStorage.removeItem(JWT_TOKEN);
      localStorage.removeItem(EXPIRES_AT);
    }
  }
}
