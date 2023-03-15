import { ErrorHandlingService } from './../../services/error-handling.service';
import { UserService } from './../../services/user.service';
import { User } from './../../interfaces/user';
import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {
  faPhone,
  faAt
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit{

  public user: User;
  public email: string;
  faPhone = faPhone;
  faAt = faAt;

  constructor(
    private userService: UserService,
    private errorHandlingService: ErrorHandlingService){}

  ngOnInit(): void {
    this.getUser();
  }

  public getUser(): void {
    this.userService.getUserInformation().subscribe({
      next: (response: User) => {
        this.user = response;
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      }
    })
  }

}
