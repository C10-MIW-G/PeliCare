import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircleService } from '../care-circle.service';
import { CareCircle } from '../carecircle';
import { TaskService } from '../services/task.service';
import { Task } from '../task';

@Component({
    selector: 'app-task',
    templateUrl: './task.component.html',
    styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit{

	public task: Task;
	public id: Number;
	public title: string;
	public description: string;
	public carecircleName: String;
	public incompleteFields: boolean;

  	public careCircleId: number;
  	constructor (
    	private route: ActivatedRoute,
    	private taskservice: TaskService,
    	private router: Router,
    	private carecircleService: CareCircleService
  	) {}

  	ngOnInit(): void {
		this.incompleteFields = false;
    	this.careCircleId = Number(this.route.snapshot.paramMap.get('circleId'));
    	this.findCircleName();    
  	}

	// to display the name of the Care Circle to which we well add this new Task
  	private findCircleName() {
    	this.carecircleService.getCareCircleById(this.careCircleId)
      	.subscribe({
        	next: (response: CareCircle) => {
          	this.carecircleName = response.name;
        	},
        	error: (error: HttpErrorResponse) => {
          		console.log(error.message);
        	}
      	});
  	}

  	saveNewTask() {
		
    	if (this.title && this.description) {      

      		this.taskservice.saveNewTaskData({
        		title: this.title,
        		description: this.description,
        		careCircleId: this.careCircleId
      		}) 
      		.subscribe({
        		complete: ()=> {
          		console.log(Response.toString);
          		this.router.navigateByUrl(`/carecircle/${this.careCircleId}`)},
          		error: ()=> {alert( "something went wrong");
        		}
      		});
      	} 
		
		else {
			this.incompleteFields = true;
		}
    }  
}
