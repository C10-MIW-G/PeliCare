import { CareCircle } from './carecircle';
import { CareCircleService } from './care-circle.service';
import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public careCircles!: CareCircle[];
constructor(private careCircleService: CareCircleService){}

  ngOnInit(): void {
    this.getCareCircles();
  }

  public getCareCircles(): void {
  this.careCircleService.getCareCircles().subscribe(
    (response: CareCircle[]) => {
      this.careCircles = response;
      console.log(this.careCircles);
    },
    (error: HttpErrorResponse) => {
      alert(error.message);
    }
  );
  }
}
