import { Task } from './../../interfaces/task';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircle } from 'src/app/interfaces/carecircle';
import { CareCircleUserStatus } from 'src/app/interfaces/carecircle-user-status';
import { CareCircleService } from 'src/app/services/care-circle.service';
import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { faTrash, faPenToSquare } from '@fortawesome/free-solid-svg-icons';
import { ImageService } from 'src/app/services/image.service';
import { ViewChild } from '@angular/core';

@Component({
	selector: 'app-carecircle-overview',
	templateUrl: './carecircle-overview.component.html',
	styleUrls: ['./carecircle-overview.component.css']
})
export class CarecircleOverviewComponent implements OnInit {

	carecirclemembers: CareCircleUserStatus[] = [];
	selectedUserStatus: CareCircleUserStatus;
	public isAdmin: Boolean;
	public isUser: Boolean;
	public adminCircles: CareCircle[];
	public userCircles: CareCircle[];
	public careCircle: CareCircle;
	public circleId: number;
	faTrash = faTrash;
	faPenToSquare = faPenToSquare;

	// data from edit modal
	newSelectedImage: any = null;
	public newName: string;
	public deleteImage: boolean = false;

	@ViewChild('myFileSelect')
	templateInputFileName: ElementRef;

	constructor(
		private route: ActivatedRoute,
		private careCircleService: CareCircleService,
		private router: Router,
		private errorHandlingService: ErrorHandlingService,
		private imageService: ImageService
	) { }

	ngOnInit(): void {
		this.route.params.subscribe(routeParams => { this.getCareCircle(routeParams['id']) });
		this.route.params.subscribe(routeParams => { this.getAllMembersOfCareCircle(routeParams['id']) });
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

	newFileSelected(event: any) {
		this.newSelectedImage = event.target.files[0];
	}

	updateCareCircle() {

		this.careCircleService.updateCareCircle(this.prepareFormData())
			.subscribe({
				complete: () => {
					this.ngOnInit();
					if (!this.deleteImage) {
						// Event for child component circleimage
						// but only called if there is an actual new image to look up.
						// Any existing image file is always named after the care circle id,
						// therefore an event is needed to reload the circleimage component,
						// which reads the (unchanged) image filename 
						// from its parent component.
						this.imageService.Stream.next(this.careCircle.imagefilename);
					}
					this.modalCleanup();					
				},
				error: (error: HttpErrorResponse) => {
					this.errorHandlingService.redirectUnexpectedErrors(error);
				}
			});
	}

	private modalCleanup() {
		this.deleteImage = false;
		this.templateInputFileName.nativeElement.value="";
	}

	private prepareFormData(): FormData {
		let editFormData = new FormData();
		editFormData.append('careCircleName', (this.newName == undefined) ?
			this.careCircle.name : this.newName);
		if (this.newSelectedImage) {
			editFormData.append('files', this.newSelectedImage);
		} else {
			editFormData.append('files', new File([], 'no file selected'));
		}
		editFormData.append('careCircleId', this.careCircle.id.toString());
		editFormData.append('oldImageFilename', this.careCircle.imagefilename);
		editFormData.append('noImage', this.deleteImage ? 'true' : 'false');

		this.newSelectedImage = undefined;		
		return editFormData;
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

	get tasksToBeDone(): Task[] {
		let tasksToBeDone: Task[];
		tasksToBeDone = this.careCircle.taskList = this.careCircle.taskList.filter(x => x.completedTask !== true);
		return tasksToBeDone;
	}

	getAllMembersOfCareCircle(id: Number): void {
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
