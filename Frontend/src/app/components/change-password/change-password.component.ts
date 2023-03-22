import { ErrorHandlingService } from '../../services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CustomvalidationService } from '../../services/custom-validation.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ChangePasswordService } from '../../services/change-password.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm: FormGroup;
  submitted: boolean;
  showErrorMessage: string;

  constructor(
    private errorHandlingService: ErrorHandlingService,
    private changepasswordservice: ChangePasswordService,
    private router: Router,
    private fb: FormBuilder,
    private customValidator: CustomvalidationService
  ){}

  ngOnInit() {
    this.changePasswordForm = this.fb.group(
      {
        oldPassword: ['', [Validators.required]],
        newPassword: ['', [Validators.required, Validators.minLength(6)]],
        newPasswordConfirm: ['', [Validators.required, Validators.minLength(6)]]
      },
      {
        validator: this.customValidator.matchNewPassword(
          'oldPassword',
          'newPassword',
          'newPasswordConfirm'
        )
      }
    )
  }

  get changePasswordFormControl(){
    return this.changePasswordForm.controls
  }

  savePass(){
    this.submitted = true;

    if (this.changePasswordForm.valid){
      this.changepasswordservice.updatePassword({
        oldPassword: this.changePasswordFormControl['oldPassword'].value,
        newPassword: this.changePasswordFormControl['newPassword'].value,
      })
      .subscribe({
        complete: ()=> {
          console.log(Response.toString);
          this.router.navigateByUrl("/carecircles")
        },
        error: (error: HttpErrorResponse)=> {
          if(error.status === 422){
          this.showErrorMessage = error.error.message
        } else {
          this.errorHandlingService.redirectUnexpectedErrors (error)
        }
         }
      });

  }

}}
