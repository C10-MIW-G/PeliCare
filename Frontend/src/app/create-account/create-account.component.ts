import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css'],
})
export class CreateAccountComponent {
  captchaResponse: string | undefined;
  password: string;
  confirmPassword: string;
  email: string;

  constructor(private authService: AuthService, private router: Router) {
    this.captchaResponse = undefined;
  }

  onSubmitRegister() {
    if(this.password === this.confirmPassword){
    this.authService.register(this.email, this.password, this.captchaResponse).subscribe({
      complete: () => {
        console.log('User is registered');
        this.router.navigateByUrl('/account/signin');
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }else{ alert('Passwords do not match');}
}
}
