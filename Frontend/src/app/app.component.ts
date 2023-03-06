import { TokenStorageService } from './services/token-storage.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

title: String = "pelicare";
constructor(
  private tokenStorageService: TokenStorageService,
  private router: Router
){}

isLoggedIn(): boolean {
  return this.tokenStorageService.isNotExpired();
}

logout() {
    this.tokenStorageService.removeToken();
        this.router.navigateByUrl('/account/signin');
  }
}
