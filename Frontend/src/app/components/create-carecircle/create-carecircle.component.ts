import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CareCircleService } from '../../services/care-circle.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-carecircle',
  templateUrl: './create-carecircle.component.html',
  styleUrls: ['./create-carecircle.component.css'],
})
export class CreateCarecircleComponent implements OnInit {

  createCircleForm: FormGroup;
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private careCircleService: CareCircleService,
    private router: Router,
    private errorHandlingService: ErrorHandlingService
  ) {}

  ngOnInit() {
    this.createCircleForm = this.fb.group({
      name: ['', Validators.required],
    });
  }

  get createCircleFormControl() {
    return this.createCircleForm.controls;
  }

  create() {
    this.submitted = true;

    if (this.createCircleForm.valid) {
      this.careCircleService
        .createNewCareCircle(this.createCircleFormControl['name'].value)
        .subscribe({
          complete: () => {
            console.log('Care Circle is created');
            this.router.navigateByUrl('/carecircles');
          },
          error: (error: HttpErrorResponse) => {
            this.errorHandlingService.redirectUnexpectedErrors(error);
          },
        });
    }
  }
}
