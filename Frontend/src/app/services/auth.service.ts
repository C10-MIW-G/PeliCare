import { TokenStorageService } from './token-storage.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

//This service receives and stores the JWT from the backend.
export class AuthService {
  private apiBackendUrl = 'http://localhost:8080';

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {}

  login(email: string, password: string) {
    this.tokenStorageService.removeToken();

    return this.http
      .post<User>(`${this.apiBackendUrl}/account/authenticate`, {
        email,
        password,
      })
      .pipe(tap((res) => this.tokenStorageService.setSession(res)));
  }

  register(email: string, password: string, captchaResponse: string | undefined) {
    return this.http.post<User>(`${this.apiBackendUrl}/account/register`, {
      email,
      password,
      captchaResponse,
    });
  }

  userEmailAvailable(userEmail: string) {
       return this.http.post<any>(`${this.apiBackendUrl}/account/validate/email`, {
         userEmail:userEmail,
       });
  }

  logout() {
    this.tokenStorageService.removeToken();
  }

}
