import { Injectable } from '@angular/core';
import { ValidatorFn, AbstractControl} from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CustomvalidationService {

  constructor(private authService: AuthService){}

  MatchPassword(password: string, confirmPassword: string) {
    return (formGroup: FormGroup) => {
      const passwordControl = formGroup.controls[password];
      const confirmPasswordControl = formGroup.controls[confirmPassword];

      if (!passwordControl || !confirmPasswordControl) {
        return null;
      }

      if (confirmPasswordControl.errors && !confirmPasswordControl.errors['passwordMismatch']) {
        return null;
      }

      if (passwordControl.value !== confirmPasswordControl.value) {
        confirmPasswordControl.setErrors({ passwordMismatch: true });
      } else {
        confirmPasswordControl.setErrors(null);
      }
    }
  }

  matchNewPassword(oldPassword: string, password: string, confirmPassword: string) {
    return (formGroup: FormGroup) => {
      const oldPasswordControl = formGroup.controls[oldPassword];
      const passwordControl = formGroup.controls[password];
      const confirmPasswordControl = formGroup.controls[confirmPassword];

      if (!passwordControl || !confirmPasswordControl) {
        return null;
      }

      if (confirmPasswordControl.errors && !confirmPasswordControl.errors['passwordMismatch']) {
        return null;
      }

      if (oldPasswordControl.value === passwordControl.value){
        passwordControl.setErrors({ passwordMismatch: true});
      } else {
        passwordControl.setErrors(null);
      }

      if (passwordControl.value !== confirmPasswordControl.value) {
        confirmPasswordControl.setErrors({ passwordMismatch: true });
      } else {
        confirmPasswordControl.setErrors(null);
      }
    }
  }

  userEmailValidator(userControl: AbstractControl) {
    return new Promise( resolve => {
      setTimeout(() => {
        this.authService.userEmailAvailable(userControl.value).subscribe(
          (result) => {
            if(!result.available){
              resolve({ userEmailNotAvailable: true });
            }
            else {
              resolve(null);
            }
          }
        )
      }, 1000);
    })
  }

}
