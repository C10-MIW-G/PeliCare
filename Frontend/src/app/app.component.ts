import { UserService } from './services/user.service';
import { ErrorHandlingService } from './services/error-handling.service';
import { CareCircleService } from './services/care-circle.service';
import { CareCircle } from './interfaces/carecircle';
import { TokenStorageService } from './services/token-storage.service';
import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from './interfaces/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  careCircles: CareCircle[];
  user: User;

title: String = "pelicare";

constructor(
  private tokenStorageService: TokenStorageService,
  private userService: UserService,
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

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe({
      next: (Response: User) => {
        this.user = Response;
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      }
    });
    return this.user;
  }
}
