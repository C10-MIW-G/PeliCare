import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircleService } from '../../services/care-circle.service';
import { CareCircle } from '../../interfaces/carecircle';
import { faPlusCircle } from '@fortawesome/free-solid-svg-icons';

@Component({
	selector: 'app-carecircle',
	templateUrl: './careCircle.component.html',
	styleUrls: ['./careCircle.component.css']
})
export class CareCircleComponent implements OnInit {

  faPlusCircle = faPlusCircle;

  public showEditButton: Boolean;
  public isAdmin: Boolean;
	public careCircle: CareCircle;

    constructor (
      private route: ActivatedRoute,
      private careCircleService: CareCircleService,
	    private router: Router,
      private errorHandlingService: ErrorHandlingService
    ) {}

	ngOnInit(): void {
		this.getCareCircle();
	}

	getCareCircle(): void {
		const id = Number(this.route.snapshot.paramMap.get('id'));
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
			complete: ()=> {
				this.router.navigateByUrl(`/carecircles`)
			},
			error: (error: HttpErrorResponse)=> {
        this.errorHandlingService.redirectUnexpectedErrors(error);
       }
		})
	}
}
