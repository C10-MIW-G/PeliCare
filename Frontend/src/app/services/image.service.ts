import { HttpClient, HttpErrorResponse, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ErrorHandlingService } from 'src/app/services/error-handling.service';

import { Observable } from "rxjs";

@Injectable({
	providedIn: 'root'
})
export class ImageService {
	private apiBackendUrl = 'http://localhost:8080';

	constructor(private http: HttpClient,
		private errorHandlingService: ErrorHandlingService) { }

	public download(filename: string): Observable<any> {
		return this.http.get(`${this.apiBackendUrl}/carecircle/download/${filename}`, {
			observe: 'body',
			responseType: 'blob'
		});
	} 

	
}