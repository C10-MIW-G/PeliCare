import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CareCircleService } from '../care-circle.service';
import { CareCircle } from '../carecircle';

@Component({
  selector: 'app-carecirclelist',
  templateUrl: './carecirclelist.component.html',
  styleUrls: ['./carecirclelist.component.css']
})
export class CarecirclelistComponent implements OnInit{
  
  public adminCircles: CareCircle[] = [];   // I am admin of these circles
  public userCircles: CareCircle[] = [];    // I am only a user here
constructor(private careCircleService: CareCircleService){}

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
            alert(error.message);
          }
    });
  }

  public getUserCircles(): void {
    this.careCircleService.getUserCircles().subscribe({
      next: (response: CareCircle[]) => {
            this.userCircles = response;
          
          },
         error: (error: HttpErrorResponse) => {
            alert(error.message);
          }
    });
  } 

}
