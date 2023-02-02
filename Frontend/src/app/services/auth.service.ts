import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { tap } from 'rxjs';
import { User } from '../user';

@Injectable({
  providedIn: 'root'
})

//This service receives and stores the JWT from the backend.
export class AuthService {

  private apiBackendUrl='http://localhost:8080';

  constructor(private http: HttpClient) {

  }

  login(email:string, password:string ) {
    //Remove previous jwt if it is still present in local storage
    if(localStorage.getItem('id_token')){
      this.logout();
    }
    return this.http.post<User>(`${this.apiBackendUrl}/account/authenticate`, {email, password})
    .pipe(tap(res => this.setSession(res)));
  }

  register(email:string, password:string) {
    return this.http.post<User>(`${this.apiBackendUrl}/account/register`, {email, password});
  }

  private setSession(authResult : any) {
      const expiresAt = moment().add(authResult.expiresIn,'second');

      localStorage.setItem('id_token', authResult.jwt);
      localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf() + 1000 * 60 * 24) );
  }

  logout() {
      localStorage.removeItem("id_token");
      localStorage.removeItem("expires_at");
  }

  public isLoggedIn() {
      return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
      return !this.isLoggedIn();
  }

  getExpiration() {
      const expiration = localStorage.getItem("expires_at");
      const expiresAt = JSON.parse(expiration!);
      return moment(expiresAt);
  }
}
