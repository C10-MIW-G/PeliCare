import { HttpClient, HttpErrorResponse, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { Observable, Subject } from "rxjs";

@Injectable({
	providedIn: 'root'
})
export class ImageService {
	private apiBackendUrl = 'http://localhost:8080';

	// to relay changes of existing images to child component of CareCircle
	public Stream: Subject<string>; 

	constructor(private http: HttpClient) {
		this.Stream = new Subject();
	 }

	public download(filename: string): Observable<any> {
		return this.http.get(`${this.apiBackendUrl}/carecircle/download/${filename}`, {
			observe: 'body',
			responseType: 'blob'
		});
	} 




	
}