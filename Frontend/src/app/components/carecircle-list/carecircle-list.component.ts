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
  public userCircles: CareCircle[];  
  faPlusCircle = faPlusCircle;
  
  constructor(
    private careCircleService: CareCircleService,
    private errorHandlingService: ErrorHandlingService,
    protected modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.getCirclesOfUser();
  }

  public getCirclesOfUser(): void {
    this.careCircleService.getAllCircles().subscribe({
      next: (response: CareCircle[]) => {
        this.userCircles = response;
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }
}


