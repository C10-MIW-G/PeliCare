import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

title: String = "pelicare";
constructor(
  private authService: AuthService,
  private router: Router
){}

isLoggedIn(): boolean {
  return this.authService.isNotExpired();
}

logout() {
    this.authService.logout();
        this.router.navigateByUrl('/account/signin');
  }
}
