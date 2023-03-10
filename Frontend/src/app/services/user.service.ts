import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiBackendUrl='http://localhost:8080';
  constructor(private http: HttpClient) { }

  public getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.apiBackendUrl}/user/currentuser`);
  }

  public deleteUser(){
    return this.http.delete(`${this.apiBackendUrl}/user/delete`);
  }

  public getUserInformation(email: string): Observable<User> {
    return this.http.get<User>(`${this.apiBackendUrl}/user/${email}`)
  }

  public editUserDetails(email: string, name: string, phoneNumber: string){
    return this.http.post(`${this.apiBackendUrl}/user/${email}/edit`, {name, phoneNumber})
  }

}
