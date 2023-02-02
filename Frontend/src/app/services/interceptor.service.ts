import { AuthService } from './auth.service';
import { HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InterceptorService {

  omitCalls = ['auth'];
  skipInterceptor = false;
  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    this.omitCalls.forEach(api => {
      if (req.url.includes(api)) {
        this.skipInterceptor = true;
      }
    });

    const idToken = localStorage.getItem('id_token');

    if (this.authService.isLoggedOut()) {
      this.authService.logout();
    }

    if (idToken || !this.skipInterceptor) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + idToken),
      });

      return next.handle(cloned);
    } else {
      return next.handle(req);
    }
  }
}
