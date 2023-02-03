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
  public careCircles!: CareCircle[];
constructor(private careCircleService: CareCircleService){}

  ngOnInit(): void {
    this.getCareCircles();
  }

  public getCareCircles(): void {
  this.careCircleService.getCareCircles().subscribe({
   next: (response: CareCircle[]) => {
      this.careCircles = response;
      console.log(this.careCircles);
    },
   error: (error: HttpErrorResponse) => {
      alert(error.message);
    }    
  });
  
  }

}
