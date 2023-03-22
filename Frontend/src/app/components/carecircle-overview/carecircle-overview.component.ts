import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircle } from 'src/app/interfaces/carecircle';
import { CareCircleUserStatus } from 'src/app/interfaces/carecircle-user-status';
import { CareCircleService } from 'src/app/services/care-circle.service';
import { ErrorHandlingService } from 'src/app/services/error-handling.service';

@Component({
  selector: 'app-carecircle-overview',
  templateUrl: './carecircle-overview.component.html',
  styleUrls: ['./carecircle-overview.component.css']
})
export class CarecircleOverviewComponent implements OnInit{

  carecirclemembers: CareCircleUserStatus[] = [];
	selectedUserStatus: CareCircleUserStatus;
  public isAdmin: Boolean;
	public isUser: Boolean;
  public adminCircles: CareCircle[];
  public userCircles: CareCircle[];
	public careCircle: CareCircle;
  public circleId: number;

  constructor(
		private route: ActivatedRoute,
		private careCircleService: CareCircleService,
		private router: Router,
		private errorHandlingService: ErrorHandlingService
	) { }

    ngOnInit(): void {
      this.route.params.subscribe(routeParams => {this.getCareCircle(routeParams['id'])})
      this.getAllMembersOfCareCircle();
    }

  getCareCircle(id: Number): void {
		this.careCircleService.getCareCircleById(id)
			.subscribe({
				next: (response: CareCircle) => {
					this.careCircle = response;
					this.checkAdminStatus();
				},
				error: (error: HttpErrorResponse) => {
					this.errorHandlingService.redirectUnexpectedErrors(error);
				}
			});
	}

	checkAdminStatus() {
		this.careCircleService.isAdmin(this.careCircle.id)
			.subscribe({
				next: (Response: boolean) => {
					this.isAdmin = Response.valueOf();
				},
				error: (error: HttpErrorResponse) => {
					this.errorHandlingService.redirectUnexpectedErrors(error);
				}
			});
	}

	deleteCareCircle() {
		this.careCircleService.deleteCareCircle(this.careCircle.id)
			.subscribe({
				complete: () => {
					this.router.navigateByUrl(`/carecircles`)
				},
				error: (error: HttpErrorResponse) => {
					this.errorHandlingService.redirectUnexpectedErrors(error);
				}
			})
	}

  get allCircles(): CareCircle[] {
    if (this.adminCircles && this.adminCircles.length > 0) {
      if (this.userCircles && this.userCircles.length > 0) {
        return this.adminCircles.concat(this.userCircles);
      } else {
        return this.adminCircles;
      }
    } else {
      return this.userCircles;
    }
  }

  getAllMembersOfCareCircle(): void {
		this.circleId = Number(this.route.snapshot.paramMap.get('id'));
		this.careCircleService.getMembersOfCareCircle(this.circleId).subscribe({
			next: (response: CareCircleUserStatus[]) => {
				this.carecirclemembers = response;
				this.checkAdminStatus();
			},
			error: (error: HttpErrorResponse) => {
				this.errorHandlingService.redirectUnexpectedErrors(error);
			},
		});
	}

  isAdminInCircle(careCircle: CareCircle) {
    var i;
    if (this.adminCircles) {
      for (i = 0; i < this.adminCircles.length; i++) {
        if (this.adminCircles[i] === careCircle) {
          return true;
        }
      }
    }

    return false;
  }

}
