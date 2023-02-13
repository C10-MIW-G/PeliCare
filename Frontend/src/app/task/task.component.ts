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

	public taskId: number;
	public title: string;
	public description: string;
	public carecircleName: String;
	public incompleteFields: boolean; 	// to display an error message if necessary
	private routeToThis: String = ""; 	// how we got here determines which api-route will be used next
	public message: String = "";		// feedback to user: what does this form do (creation or update of Task)	
	private newTask: boolean = false; 	// making or editing requires different data and api calls
  	public careCircleId: number;
	public completedTask: boolean;

  	constructor (
    	private route: ActivatedRoute,
    	private taskservice: TaskService,
    	private router: Router,
    	private carecircleService: CareCircleService
  	) {}

  	ngOnInit(): void {
		this.incompleteFields = false;	
		this.careCircleId = Number(this.route.snapshot.paramMap.get('circleId'));
		this.routeToThis = this.route.toString();
		
		if(this.routeToThis.includes("create")){ this.prepareCreationOfTask(); }
		if(this.routeToThis.includes("edit")){ this.prepareEditingOfTask(); }	

    	this.displayCircleName();    
  	}

	private prepareEditingOfTask() {
		this.message = "Edit Task";
		this.taskId = Number(this.route.snapshot.paramMap.get('taskId'));
		this.taskservice.getTaskById(this.taskId)
			.subscribe({
				next: (response: Task) => {
					this.title = response.title;
					this.description = response.description;
					this.completedTask = response.completedTask;
				},
				error: (error: HttpErrorResponse) => { console.log(error.message); }
			});
	}

	private prepareCreationOfTask() {
		this.newTask = true;
		this.message = "Create new Task";
	}
	
  	private displayCircleName() {
    	this.carecircleService.getCareCircleById(this.careCircleId)
      	.subscribe({
        	next: (response: CareCircle) => {
          		this.carecircleName = response.name;
        	},
        	error: (error: HttpErrorResponse) => { console.log(error.message); }
      	});
  	}

	save() {
		if(this.title && this.description) {
			if (this.newTask) { this.saveNewTask()}
			else { this.updateTask()}
		}
		else { this.incompleteFields = true; }
	}

	updateTask() {
		this.taskservice.updateTask({
			title: this.title,
			description: this.description,
			id: this.taskId,
			completedTask: false
		}).subscribe({
			complete: ()=> {
				console.log(Response.toString);
				this.router.navigateByUrl(`/carecircle/${this.careCircleId}`)
			},
			error: ()=> {alert( "something went wrong"); }
		});
	}

  	saveNewTask() {	      

		this.taskservice.saveNewTaskData({
			title: this.title,
			description: this.description,
			careCircleId: this.careCircleId
		}) 
		.subscribe({
			complete: ()=> {
				console.log(Response.toString);
				this.router.navigateByUrl(`/carecircle/${this.careCircleId}`)
			},
			error: ()=> {alert( "something went wrong"); }
		});    		
    }  

	delete() {
		this.taskservice.deleteTask(this.taskId)
		.subscribe({
			complete: ()=> {
				console.log(Response.toString);
				this.router.navigateByUrl(`/carecircle/${this.careCircleId}`)
			},
			error: ()=> {alert( "something went wrong"); }
		})
	}
}
