import { UserService } from './../../services/user.service';
import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute} from '@angular/router';
import { CareCircleService } from '../../services/care-circle.service';
import { CareCircleUserStatus } from 'src/app/interfaces/carecircle-user-status';
import { User } from 'src/app/interfaces/user';

@Component({
	selector: 'app-carecircle-members',
	templateUrl: './carecircle-members.component.html',
	styleUrls: ['./carecircle-members.component.css'],
})
export class CarecircleMembersComponent implements OnInit {
	form: FormGroup;
	carecirclemembers: CareCircleUserStatus[] = [];
	selectedUserStatus: CareCircleUserStatus;
	numberOfAdmins: number;
	public isAdmin: Boolean;
	errorMessage: string;
	submitted: boolean;
	circleId: number;
  currentUser: string;

	constructor(
		private route: ActivatedRoute,
		private fb: FormBuilder,
		private careCircleService: CareCircleService,
		private errorHandlingService: ErrorHandlingService,
    private userService: UserService
	) {
		this.form = this.fb.group({
			email: ['', Validators.required],
		});
	}
	ngOnInit(): void {
		this.getAllMembersOfCareCircle();
		// the template modal data need to have some initialisation
		this.selectedUserStatus = { email: "test mail", isAdmin: false, circleId: -1 };
	}

	get formControl() {
		return this.form.controls;
	}

	getAllMembersOfCareCircle(): void {
		this.circleId = Number(this.route.snapshot.paramMap.get('id'));
		this.careCircleService.getMembersOfCareCircle(this.circleId).subscribe({
			next: (response: CareCircleUserStatus[]) => {
				this.carecirclemembers = response;
				this.checkAdminStatus();
        this.getCurrentUser();
			},
			error: (error: HttpErrorResponse) => {
				this.errorHandlingService.redirectUnexpectedErrors(error);
			},
		});
	}

	addUserToCareCircle() {
		this.submitted = true;
		if (this.form.valid) {
			const id = Number(this.route.snapshot.paramMap.get('id'));
			const val = this.form.value;
			this.careCircleService.addUserToCareCircle(id, val.email).subscribe({
				complete: () => {
					console.log('User is added');
					this.reload();
				},
				error: (error: HttpErrorResponse) => {
					if (error.status === 409) {
						this.errorMessage = error.error.message;
					}
					if (error.status === 404) {
						this.errorMessage = error.error.message;
					} else {
						this.errorHandlingService.redirectUnexpectedErrors(error);
					}
				},
			});
		}
	}

  removeUserFromCareCircle(user: CareCircleUserStatus) {
    const id = Number(this.route.snapshot.paramMap.get('id'))
    var userEmail = user.email
    this.careCircleService.removeUserFromCareCircle(id, userEmail).subscribe({
      complete: () => {
        console.log('User is removed');
        this.reload();
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      }
    })
  }

	showModal(userstatus: CareCircleUserStatus) {
		this.selectedUserStatus = userstatus;
		this.countNumberOfAdminsInCircle();

	}

	countNumberOfAdminsInCircle() {
		this.numberOfAdmins = 0;
		this.carecirclemembers.forEach((ccm) => {
			if (ccm.isAdmin) { this.numberOfAdmins += 1 }
		});
	}

	checkAdminStatus() {
		const id = Number(this.route.snapshot.paramMap.get('id'));
		this.careCircleService.isAdmin(id).subscribe({
			next: (Response: boolean) => {
				this.isAdmin = Response.valueOf();
			},
			error: (error: HttpErrorResponse) => {
				this.errorHandlingService.redirectUnexpectedErrors(error);
			},
		});
	}

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe({
      next: (Response: User) => {
        this.currentUser = Response.email;
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      }
    });

    return this.currentUser;
  }

	reload() {
		this.ngOnInit();
	}

	toggleAdminStatus(email: String) {
		this.careCircleService.toggleAdminStatus({
			email: email,
			circleId: this.circleId
		})
		.subscribe({
			complete: () => {
				console.log("admin status of user is turned around");
				this.reload();
			},
			error: (error: HttpErrorResponse) => {
				this.errorHandlingService.redirectUnexpectedErrors(error);
			}
		});
	}
}
