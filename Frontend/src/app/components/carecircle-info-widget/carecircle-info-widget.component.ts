import { ErrorHandlingService } from './../../services/error-handling.service';
import { CareCircleService } from './../../services/care-circle.service';
import { CareCircle } from './../../interfaces/carecircle';
import { Component, Input, OnInit } from '@angular/core';
import {
  faTasks,
  faUser,
  faUsers,
  faUserShield,
} from '@fortawesome/free-solid-svg-icons';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'info-widget',
  templateUrl: './carecircle-info-widget.component.html',
  styleUrls: ['./carecircle-info-widget.component.css'],
})
export class CarecircleInfoWidgetComponent implements OnInit {
  @Input() careCircle: CareCircle;
  @Input() isAdmin: boolean;
  faUserShield = faUserShield;
  faUser = faUser;
  faTasks = faTasks;
  faUsers = faUsers;
  memberCount: number;

  constructor(
    private careCircleService: CareCircleService,
    private errorHandlingService: ErrorHandlingService
  ) {}

  ngOnInit() {
    this.getMemberCount();
  }

  getMemberCount() {
    this.careCircleService
      .getMembersOfCareCircle(this.careCircle.id)
      .subscribe({
        next: (result: any) => {
          this.memberCount = result.length;
        },
        error: (error: HttpErrorResponse) => {
          this.errorHandlingService.redirectUnexpectedErrors(error);
        },
      });
  }

  get taskCountMessage() {
    if (this.careCircle.taskList.length == 0) {
      return 'This circle has no tasks';
    } else if (this.careCircle.taskList.length == 1) {
      return 'This circle has 1 task';
    } else {
      return 'This circle has ' + this.careCircle.taskList.length + ' tasks';
    }
  }

  get memberCountMessage() {
    if (this.memberCount == 1) {
      return 'You are the only member of this circle';
    } else {
      return 'This circle has ' + this.memberCount + ' members';
    }
  }
}
