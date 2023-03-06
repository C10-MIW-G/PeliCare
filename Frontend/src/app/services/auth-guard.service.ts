import { TokenStorageService } from './token-storage.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  constructor(private tokenStorageService: TokenStorageService, private router: Router) { }

  canActivate(): boolean {
    if(this.tokenStorageService.getToken() === null || this.tokenStorageService.isExpired()){
      this.router.navigateByUrl('/account/signin');
      return false;
    }

    return true;
  }

}
