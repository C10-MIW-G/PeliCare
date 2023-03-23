import { Task } from './../../interfaces/task';
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
  isAdmin: boolean;
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
    this.getAdminStatus();
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

  getAdminStatus() {
    this.careCircleService
      .isAdmin(this.careCircle.id)
      .subscribe({
        next: (result: boolean) => {
          this.isAdmin = result;
        },
        error: (error: HttpErrorResponse) => {
          this.errorHandlingService.redirectUnexpectedErrors(error);
        }
      });
  }

  get countTasksToBeDone(): number {
      let tasksToBeDone: Task[];
      tasksToBeDone = this.careCircle.taskList = this.careCircle.taskList.filter(x => x.completedTask !== true);
      return tasksToBeDone.length;
  }

  get taskCountMessage(): string {
    if (this.countTasksToBeDone == 0) {
      return 'This circle has no unfinished tasks';
    } else if (this.countTasksToBeDone == 1) {
      return 'This circle has 1 unfinished task';
    } else {
      return 'This circle has ' + this.countTasksToBeDone + ' unfinished tasks';
    }
  }

  get memberCountMessage(): string {
    if (this.memberCount == 1) {
      return 'You are the only member of this circle';
    } else {
      return 'This circle has ' + this.memberCount + ' members';
    }
  }
}
