import { ModalService } from './../../services/modal.service';
import { faPlusCircle } from '@fortawesome/free-solid-svg-icons';
import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CareCircleService } from '../../services/care-circle.service';
import { CareCircle } from '../../interfaces/carecircle';

@Component({
  selector: 'app-carecirclelist',
  templateUrl: './carecircle-list.component.html',
  styleUrls: ['./carecircle-list.component.css'],
})
export class CarecirclelistComponent implements OnInit {
  public adminCircles: CareCircle[];
  public userCircles: CareCircle[];
  faPlusCircle = faPlusCircle;

  constructor(
    private careCircleService: CareCircleService,
    private errorHandlingService: ErrorHandlingService,
    protected modalService: ModalService
  ) {}

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
      },
    });
  }

  public getUserCircles(): void {
    this.careCircleService.getUserCircles().subscribe({
      next: (response: CareCircle[]) => {
        this.userCircles = response;
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }

  isAdminInCircle(careCircle: CareCircle) {
    var i;
    if (this.adminCircles) {
      for (i = 0; i < this.adminCircles.length; i++) {
        if (this.adminCircles[i] === careCircle) {
          return true;
        }
      }
    }

    return false;
  }

  get allCircles(): CareCircle[] {
    if (this.adminCircles && this.adminCircles.length > 0) {
      if (this.userCircles && this.userCircles.length > 0) {
        return this.adminCircles.concat(this.userCircles);
      } else {
        return this.adminCircles;
      }
    } else {
      return this.userCircles;
    }
  }

}
