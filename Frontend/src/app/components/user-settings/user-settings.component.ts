import { ActivatedRoute } from '@angular/router';
import { ErrorHandlingService } from './../../services/error-handling.service';
import { UserService } from './../../services/user.service';
import { faTrash, faPhone, faAt, faUserEdit, faPenToSquare } from '@fortawesome/free-solid-svg-icons';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from 'src/app/interfaces/user';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent {
  userDetailsForm: FormGroup;
  faTrash = faTrash;
  faPhone = faPhone;
  faAt = faAt;
  faUserEdit = faUserEdit;
  faPenToSquare = faPenToSquare;
  email = String(this.route.snapshot.paramMap.get('email'))

  user: User;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private errorHandlingService: ErrorHandlingService,
    private route: ActivatedRoute){}

  ngOnInit(): void {
  this.userDetailsForm = this.fb.group({
    name: [''],
    phoneNumber: [''],
  });
  this.getUser();
}

get userDetailsFormControl() {
  return this.userDetailsForm.controls;
}

public getUser(): User {
  this.userService.getUserInformation().subscribe({
    next: (response: User) => {
      this.user = response;
    },
    error: (error: HttpErrorResponse) => {
      this.errorHandlingService.redirectUnexpectedErrors(error);
    }
  })
  return this.user;
}

fillUserDetailsForm(): void{
  this.userDetailsForm.controls['name'].setValue(this.user.name);
  this.userDetailsForm.controls['phoneNumber'].setValue(this.user.phoneNumber);
}

onSubmitEdit() {
  if (this.userDetailsForm.valid) {
    this.userService.editUserDetails(this.userDetailsFormControl['name'].value, this.userDetailsFormControl['phoneNumber'].value)
    .subscribe({
      next: () => {
        window.location.reload();
      },
      error: (error: HttpErrorResponse) => {
          this.errorHandlingService.redirectUnexpectedErrors(error);
        }
    });
  }
}
}
