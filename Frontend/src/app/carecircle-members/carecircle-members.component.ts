import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircleService } from '../care-circle.service';
import { CareCircleUserStatus } from '../carecircle-user-status';

@Component({
	selector: 'app-carecircle-members',
	templateUrl: './carecircle-members.component.html',
	styleUrls: ['./carecircle-members.component.css'],
})
export class CarecircleMembersComponent implements OnInit {
	form: FormGroup;
	carecirclemembers: CareCircleUserStatus[] = [];
	public isAdmin: Boolean;
	errorMessage: string;
	submitted: boolean;
	circleId: Number

	constructor(
		private route: ActivatedRoute,
		private fb: FormBuilder,
		private careCircleService: CareCircleService,
		private errorHandlingService: ErrorHandlingService
	) {
		this.form = this.fb.group({
			email: ['', Validators.required],
		});
	}
	ngOnInit(): void {
		this.getAllMembersOfCareCircle();
	}

	get formControl() {
		return this.form.controls;
	}


	getAllMembersOfCareCircle(): void {
		const id = Number(this.route.snapshot.paramMap.get('id'));
		this.circleId = id; // used in promoting a user to Circle Admin
		this.careCircleService.getMembersOfCareCircle(id).subscribe({
			next: (response: CareCircleUserStatus[]) => {
				this.carecirclemembers = response;
				this.checkAdminStatus();
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

	reload() {
		this.ngOnInit();
	}


	makeAdmin(email: String) {
		this.careCircleService.makeUserCircleAdmin({
			"email": email,
			"circleId": this.circleId
		})
			.subscribe({
				complete: () => {
					console.log("regel 103 van carecircle-members.component.ts is bereikt");
					console.log(Response);
					this.reload()
				},
				error: (error: HttpErrorResponse) => {
					this.errorHandlingService.redirectUnexpectedErrors(error);
				}

			});
	}

	revokeAdmin(email: String) {
		this.careCircleService.revokeUserAdmin({
			"email": email,
			"circleId": this.circleId
		})
			.subscribe({
				complete: () => {
					console.log("regel 103 van carecircle-members.component.ts is bereikt");
					console.log(Response);
					this.reload()
				},
				error: (error: HttpErrorResponse) => {
					this.errorHandlingService.redirectUnexpectedErrors(error);
				}
			});
	}
}

