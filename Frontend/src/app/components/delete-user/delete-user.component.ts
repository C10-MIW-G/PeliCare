import { ModalService } from './../../services/modal.service';
import { ErrorHandlingService } from './../../services/error-handling.service';
import { TokenStorageService } from './../../services/token-storage.service';
import { UserService } from './../../services/user.service';
import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.css'],
})
export class DeleteUserComponent {

  careCircles: string[];

  constructor(
    private userService: UserService,
    private tokenStorageServer: TokenStorageService,
    private errorHandlingService: ErrorHandlingService,
    private router: Router,
    protected modalService: ModalService
  ) {}

  deleteUser() {
    this.userService.deleteUser().subscribe({
      complete: () => {
        this.tokenStorageServer.removeToken();
        this.router.navigateByUrl('');
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 409) {
          this.careCircles = error.error.errorObject;
          this.modalService.open('deleteUserWarning');
        } else {
          this.errorHandlingService.redirectUnexpectedErrors(error);
        }
      },
    });
  }
}
