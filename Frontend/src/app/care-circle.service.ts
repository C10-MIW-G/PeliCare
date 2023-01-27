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

  public getCareCircles(): Observable<CareCircle[]> {
    return this.http.get<CareCircle[]>(`${this.apiBackendUrl}/carecircle/all`);
  }
}
