import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircleService } from '../../services/care-circle.service';
import { User } from '../../interfaces/user';

@Component({
  selector: 'app-carecircle-members',
  templateUrl: './carecircle-members.component.html',
  styleUrls: ['./carecircle-members.component.css'],
})
export class CarecircleMembersComponent implements OnInit {
  form: FormGroup;
  users: User[] = [];
  public isAdmin: Boolean;
  errorMessage: string;
  submitted: boolean;

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
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.careCircleService.getMembersOfCareCircle(id).subscribe({
      next: (response: User[]) => {
        this.users = response;
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
}
