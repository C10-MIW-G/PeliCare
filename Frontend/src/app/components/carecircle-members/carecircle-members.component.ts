import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircleService } from '../../services/care-circle.service';
import { CareCircleUserStatus } from 'src/app/services/carecircle-user-status';

@Component({
  selector: 'app-carecircle-members',
  templateUrl: './carecircle-members.component.html',
  styleUrls: ['./carecircle-members.component.css'],
})
export class CarecircleMembersComponent implements OnInit {
  form: FormGroup;
  carecirclemembers: CareCircleUserStatus[] = [];
  public isAdmin: Boolean;
  errorMessage: string;
  submitted: boolean;
  circleId: number;
  lastAdminDeleteWarning: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private careCircleService: CareCircleService,
    private errorHandlingService: ErrorHandlingService
  ) {
    this.form = this.fb.group({
      email: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    this.getAllMembersOfCareCircle();
  }

  get formControl() {
    return this.form.controls;
  }

  getAllMembersOfCareCircle(): void {
    this.circleId = Number(this.route.snapshot.paramMap.get('id'));
    this.careCircleService.getMembersOfCareCircle(this.circleId).subscribe({
      next: (response: CareCircleUserStatus[]) => {
        this.carecirclemembers = response;
        this.checkAdminStatus();
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }

  addUserToCareCircle() {
    this.submitted = true;
    if (this.form.valid) {
      const id = Number(this.route.snapshot.paramMap.get('id'));
      const val = this.form.value;
      this.careCircleService.addUserToCareCircle(id, val.email).subscribe({
        complete: () => {
          console.log('User is added');
          this.reload();
        },
        error: (error: HttpErrorResponse) => {
          if (error.status === 409) {
            this.errorMessage = error.error.message;
          }
          if (error.status === 404) {
            this.errorMessage = error.error.message;
          } else {
            this.errorHandlingService.redirectUnexpectedErrors(error);
          }
        },
      });
    }
  }

  checkAdminStatus() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.careCircleService.isAdmin(id).subscribe({
      next: (Response: boolean) => {
        this.isAdmin = Response.valueOf();
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }

  reload() {
    this.ngOnInit();
  }  

  toggleAdminStatus(email: String) {
    this.lastAdminDeleteWarning = false; // new try after possible mistake: remove any previous error message
    this.careCircleService.toggleAdminStatus({
      email: email,
      circleId: this.circleId
    })
    .subscribe({
      complete: () => {
        console.log("admin status of user is turned around");
        this.reload();
      },
      error: (error: HttpErrorResponse) =>{
        if(error.status === 500){
         this.lastAdminDeleteWarning = true;
        } else {
          this.errorHandlingService.redirectUnexpectedErrors(error);
        }        
      }
    });
  }
}
