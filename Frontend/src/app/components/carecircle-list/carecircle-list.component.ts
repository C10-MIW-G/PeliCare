import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CareCircleService } from '../../services/care-circle.service';
import { CareCircle } from '../../interfaces/carecircle';

@Component({
  selector: 'app-carecirclelist',
  templateUrl: './carecircle-list.component.html',
  styleUrls: ['./carecircle-list.component.css']
})
export class CarecirclelistComponent implements OnInit{

  public adminCircles: CareCircle[]; // I am admin of these circles
  public userCircles: CareCircle[]; // I am only a user here

constructor(
  private careCircleService: CareCircleService,
  private errorHandlingService: ErrorHandlingService
  ){}

  ngOnInit(): void {
    this.getAdminCircles();
    this.getUserCircles();
  }

  public getAdminCircles(): void {
    this.careCircleService.getAdminCircles().subscribe({
      next: (response: CareCircle[]) => {
            this.adminCircles = response;
          },
         error: (error: HttpErrorResponse) => {
          this.errorHandlingService.redirectUnexpectedErrors(error);
          }
    });
  }

  public getUserCircles(): void {
    this.careCircleService.getUserCircles().subscribe({
      next: (response: CareCircle[]) => {
            this.userCircles = response;
          },
         error: (error: HttpErrorResponse) => {
            this.errorHandlingService.redirectUnexpectedErrors(error);
          }
    });
  }

}
