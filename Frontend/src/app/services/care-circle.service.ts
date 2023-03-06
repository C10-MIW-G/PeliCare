import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { CareCircle } from '../interfaces/carecircle';
import { CareCircleUserStatus } from '../interfaces/carecircle-user-status';
import { ToggleUserAdmin } from '../interfaces/toggle-user-admin';

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

  public addUserToCareCircle( id: Number, email: string) {
    return this.http.post(`${this.apiBackendUrl}/carecircle/get/${id}/members/add`, {email});
  }

  public getMembersOfCareCircle( id: Number): Observable<CareCircleUserStatus[]> {
    return this.http.get<CareCircleUserStatus[]>(`${this.apiBackendUrl}/carecircle/get/${id}/members`);
  }

  public toggleAdminStatus(toggleuseradmin: ToggleUserAdmin): Observable<String> {
    return this.http.patch<String>(`${this.apiBackendUrl}/carecircle/toggleadminstatus`,{
      email: toggleuseradmin.email,
      circleId: toggleuseradmin.circleId
    });
  }

  public removeUserFromCareCircle( id: Number, userEmail: string) {
    return this.http.delete(`${this.apiBackendUrl}/carecircle/get/${id}/members/remove/${userEmail}`);
  }
}
