import { ActivatedRoute, Router } from '@angular/router';
import { Component, NgModule } from '@angular/core';
import { ChangePasswordService } from '../services/change-password.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  oldPassword: string;
  newPassword: string;
  newPasswordConfirm: string;
  public showErrorMessage: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private changepasswordservice: ChangePasswordService,
    private router: Router,
  ){}

  savePass(){
    if (this.newPassword === this.newPasswordConfirm){
      this.changepasswordservice.updatePassword({
        oldPassword: this.oldPassword,
        newPassword: this.newPassword,
      })
      .subscribe({
        complete: ()=> {
          console.log(Response.toString);
          this.router.navigateByUrl("/carecircles")
        },
        error: ()=> {alert( "something went wrong"); }
      });
    } else {
      this.showErrorMessage = true
    }
  }

}
