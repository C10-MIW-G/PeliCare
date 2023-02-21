import { ErrorHandlingService } from '../../services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css'],
})
export class SignInComponent implements OnInit{

  signInForm: FormGroup;
  submitted = false;
  signInInvalidMessage : String;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private errorHandlingSevice : ErrorHandlingService,
    private router: Router
  ) {}

   ngOnInit(): void {
    this.signInForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  get signInFormControl() {
    return this.signInForm.controls;
  }

  onSubmitLogin() {
    this.submitted = true;
    if (this.signInForm.valid) {
      this.authService.login(this.signInFormControl['email'].value, this.signInFormControl['password'].value)
      .subscribe({
        next: () => {
          this.router.navigateByUrl('/carecircles');
        },
        error: (error: HttpErrorResponse) => {
          if(error.status === 422){
            this.signInInvalidMessage = error.error.message;
          }
          else {
            this.errorHandlingSevice.redirectUnexpectedErrors(error);
          }
        },
      });
    }
  }
}
