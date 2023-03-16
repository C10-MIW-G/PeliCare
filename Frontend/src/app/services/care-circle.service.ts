import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http'
import { Observable } from 'rxjs';
import { CareCircle } from '../interfaces/carecircle';
import { CareCircleUserStatus } from '../interfaces/carecircle-user-status';
import { ToggleUserAdmin } from '../interfaces/toggle-user-admin';

@Injectable({
	providedIn: 'root'
})
export class CareCircleService {
    
  private apiBackendUrl='http://localhost:8080';
  constructor(private http: HttpClient) { 
  }

	public getAdminCircles(): Observable<CareCircle[]> {
		return this.http.get<CareCircle[]>(`${this.apiBackendUrl}/carecircle/admin`);
	}

	public getUserCircles(): Observable<CareCircle[]> {
		return this.http.get<CareCircle[]>(`${this.apiBackendUrl}/carecircle/user`);
	}

	public getAllCircles(): Observable<CareCircle[]> {
		return this.http.get<CareCircle[]>(`${this.apiBackendUrl}/carecircle/all`);
	}

	public getCareCircleById(id: Number): Observable<CareCircle> {
		return this.http.get<CareCircle>(`${this.apiBackendUrl}/carecircle/get/${id}`);
	}

	public createNewCareCircle(name: string) {
		return this.http.post(`${this.apiBackendUrl}/carecircle/create`, { name });
	}

	public uploadFile(formdata: FormData) {

		return this.http.post(`${this.apiBackendUrl}/carecircle/upload`, formdata);
	}

	public isUser(circleId: Number): Observable<boolean> {
		return this.http.get<boolean>(`${this.apiBackendUrl}/carecircle/isuser/${circleId}`);
	}

	public deleteCareCircle(circleId: Number) {
		return this.http.delete(`${this.apiBackendUrl}/carecircle/delete/${circleId}`);
	}

	public isAdmin(circleId: Number): Observable<boolean> {
		return this.http.get<boolean>(`${this.apiBackendUrl}/carecircle/isadmin/${circleId}`);
	}	

	public addUserToCareCircle(id: Number, email: string) {
		return this.http.post(`${this.apiBackendUrl}/carecircle/get/${id}/members/add`, { email });
	}

	public getMembersOfCareCircle(id: Number): Observable<CareCircleUserStatus[]> {
		return this.http.get<CareCircleUserStatus[]>(`${this.apiBackendUrl}/carecircle/get/${id}/members`);
	}

	public toggleAdminStatus(toggleuseradmin: ToggleUserAdmin): Observable<String> {
		return this.http.patch<String>(`${this.apiBackendUrl}/carecircle/toggleadminstatus`, {
			email: toggleuseradmin.email,
			circleId: toggleuseradmin.circleId
		});
	}

	// functie om een bestand te downloaden
	public download(filename: string): Observable<HttpEvent<Blob>> {
		return this.http.get(`${this.apiBackendUrl}/carecircle/download/${filename}`, {
			reportProgress: true,
			observe: 'events',
			responseType: 'blob'
		});
	}
  public removeUserFromCareCircle( id: Number, userEmail: string) {
    return this.http.delete(`${this.apiBackendUrl}/carecircle/get/${id}/members/remove/${userEmail}`);
  }

  public removeYourselfFromCareCircle(id: number, userEmail: string) {
	  return this.http.delete(`${this.apiBackendUrl}/carecircle/get/${id}/members/removeyourself/${userEmail}`);
  }
}
