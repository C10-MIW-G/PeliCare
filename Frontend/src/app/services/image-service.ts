import { HttpClient, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
	providedIn: 'root'
})
export class ImageService {
	private apiBackendUrl = 'http://localhost:8080';
	constructor(private http: HttpClient) { }

    // function borrowed from: https://www.youtube.com/watch?v=n26StCRoeHA
    public download(filename: string): Observable<HttpEvent<Blob>> {
		return this.http.get(`${this.apiBackendUrl}/carecircle/download/${filename}`, {
			reportProgress: true,
			observe: 'events',
			responseType: 'blob'
		});
	} 
}