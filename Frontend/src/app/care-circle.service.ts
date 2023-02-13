import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { CareCircle } from './carecircle';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class CareCircleService {
  private apiBackendUrl='http://localhost:8080';
  constructor(private http: HttpClient) { }

  public getCareCircles(): Observable<CareCircle[]> {
    return this.http.get<CareCircle[]>(`${this.apiBackendUrl}/carecircle/all`);
  }

  public getCareCircleById( id: Number): Observable<CareCircle> {
    return this.http.get<CareCircle>(`${this.apiBackendUrl}/carecircle/get/${id}`);
  }

  public createNewCareCircle(name: string) {
    return this.http.post(`${this.apiBackendUrl}/carecircle/create`, {name});
  }

  public addUserToCareCircle(email: string){
    return this.http.post(`${this.apiBackendUrl}/carecircle/adduser`, {email})
  }

}
