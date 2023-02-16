import { NavigationExtras, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlingService {

  constructor(private redirectRoute: Router) { }

  redirectUnexpectedErrors(error: HttpErrorResponse){
    if(error.error.message !== undefined){
      const navigationExtras: NavigationExtras = {state: error.error};
      this.redirectRoute.navigate(['error'], navigationExtras);
    }
    else{
      this.redirectRoute.navigateByUrl('/notfound');
    }
  }

}
