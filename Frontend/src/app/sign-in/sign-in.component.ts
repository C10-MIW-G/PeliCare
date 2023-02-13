import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css'],
})
export class SignInComponent implements OnInit{
  form: FormGroup;
  public incompleteEmailField: boolean; 
    public incompletePasswordField: boolean;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    this.incompleteEmailField = false;
    this.incompletePasswordField = false;    
  }  

  login() {
    const val = this.form.value;
    if (!val.email){
      this.incompleteEmailField = true;
    }

    if (!val.password){
      this.incompletePasswordField = true;
    }

    if (val.email && val.password) {
      this.authService.login(val.email, val.password).subscribe({
        next: () => {
          console.log('User is logged in');
          this.router.navigateByUrl('/carecircles');
        },
        error: () => {
          alert('Username or password are incorrect.');
        },
      });
    }
  }
}
