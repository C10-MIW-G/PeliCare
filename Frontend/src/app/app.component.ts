import { ErrorHandlingService } from './services/error-handling.service';
import { CareCircleService } from './services/care-circle.service';
import { CareCircle } from './interfaces/carecircle';
import { TokenStorageService } from './services/token-storage.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  careCircles: CareCircle[];

title: String = "pelicare";
constructor(
  private tokenStorageService: TokenStorageService,
  private router: Router,
  private careCircleService: CareCircleService,
  private errorHandlingService: ErrorHandlingService
){}

isLoggedIn(): boolean {
  return this.tokenStorageService.isNotExpired();
}

logout() {
    this.tokenStorageService.removeToken();
  }

getCareCircles(){
    this.careCircleService.getAllCircles().subscribe({
      next: (response: CareCircle[]) => {
        this.careCircles = response;

      },
     error: (error: HttpErrorResponse) => {
      this.errorHandlingService.redirectUnexpectedErrors(error);
      }
    });
  }
}
