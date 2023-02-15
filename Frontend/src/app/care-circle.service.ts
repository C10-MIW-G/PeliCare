import { Task } from './task';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { CareCircle } from './carecircle';

@Injectable({
  providedIn: 'root'
})
export class CareCircleService {
  private apiBackendUrl='http://localhost:8080';
  constructor(private http: HttpClient) { }

  public getAdminCircles(): Observable<CareCircle[]> {
    return this.http.get<CareCircle[]>(`${this.apiBackendUrl}/carecircle/admin`);
  }

  public getUserCircles(): Observable<CareCircle[]> {
    return this.http.get<CareCircle[]>(`${this.apiBackendUrl}/carecircle/user`);
  }

  public getCareCircleById( id: Number): Observable<CareCircle> {
    return this.http.get<CareCircle>(`${this.apiBackendUrl}/carecircle/get/${id}`);
  }

  public createNewCareCircle(name: string) {
    return this.http.post(`${this.apiBackendUrl}/carecircle/create`, {name});
  }

  public isAdmin(circleId: Number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiBackendUrl}/carecircle/isadmin/${circleId}`);    
  }

  public deleteCareCircle(circleId: Number) {
    return this.http.delete(`${this.apiBackendUrl}/carecircle/delete/${circleId}`);
  }

}
