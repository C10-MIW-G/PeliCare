import { Observable } from 'rxjs';
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ChangePasswords } from "../interfaces/change-password";


@Injectable({providedIn: 'root'})
export class ChangePasswordService {
  private apiBackendUrl='http://localhost:8080';
  constructor(private http: HttpClient) { }

  public updatePassword( changePasswords: ChangePasswords): Observable<ChangePasswords> {
    return this.http.post<ChangePasswords> (`${this.apiBackendUrl}/user/updatepassword`, {
      oldPassword: changePasswords.oldPassword,
      newPassword: changePasswords.newPassword,
    })
  }
}
