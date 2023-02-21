import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { CustomvalidationService } from './../../services/custom-validation.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css'],
})
export class CreateAccountComponent implements OnInit {
  registerForm: FormGroup;
  submitted = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private customValidator: CustomvalidationService,
    private errorHandlingService: ErrorHandlingService
  ) {}

  ngOnInit() {
    this.registerForm = this.fb.group(
      {
        email: ['', [Validators.required, Validators.email], this.customValidator.userEmailValidator.bind(this.customValidator)],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]],
        captchaResponse: ['', [Validators.required]],
      },
      {
        validator: this.customValidator.MatchPassword(
          'password',
          'confirmPassword'
        ),
      }
    );
  }

  get registerFormControl() {
    return this.registerForm.controls;
  }

  onSubmitRegister() {
    this.submitted = true;

    if (this.registerForm.valid) {
      this.authService
        .register(
          this.registerFormControl['email'].value,
          this.registerFormControl['password'].value,
          this.registerFormControl['captchaResponse'].value
        )
        .subscribe({
          complete: () => {
            this.router.navigateByUrl('/account/signin');
          },
          error: (error: HttpErrorResponse) => {
            this.errorHandlingService.redirectUnexpectedErrors(error);
          },
        });
    }
  }
}
