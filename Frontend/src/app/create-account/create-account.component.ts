import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent {
  form:FormGroup;

  constructor(private fb:FormBuilder,
               private authService: AuthService,
               private router: Router) {

      this.form = this.fb.group({
          email: ['',Validators.required],
          password: ['',Validators.required]
      });
  }

  register() {
      const val = this.form.value;

      if (val.email && val.password) {
          this.authService.register(val.email, val.password)
              .subscribe({
                  complete: () => {
                      console.log("User is registered");
                      this.router.navigateByUrl('/account/signin');
                  },
                error: () => {
                  alert("Email address is already in use.");
                }}
              );
      }
  }
}
