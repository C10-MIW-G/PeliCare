import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
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

  taskForm: FormGroup;
  submitted = false;

	public taskId: number;
	public carecircleName: String;
	private routeToThis: String = ""; 	// how we got here determines which api-route will be used next
	public message: String = "";		// feedback to user: what does this form do (creation or update of Task)
	private newTask: boolean = false; 	// making or editing requires different data and api calls
  public careCircleId: number;
	public completedTask: boolean;

  constructor (
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private taskService: TaskService,
    private router: Router,
    private carecircleService: CareCircleService,
    private errorHandlingService: ErrorHandlingService
  ) {}

  	ngOnInit(): void {
		this.incompleteFields = false;
		this.careCircleId = Number(this.route.snapshot.paramMap.get('circleId'));
		this.routeToThis = this.route.toString();

		if(this.routeToThis.includes("create")){ this.prepareCreationOfTask(); }
		if(this.routeToThis.includes("edit")){ this.prepareEditingOfTask(); }

    	this.displayCircleName();
  	}

  get taskFormControl(){
    return this.taskForm.controls;
  }

	private prepareEditingOfTask() {
		this.message = "Edit Task";
		this.taskId = Number(this.route.snapshot.paramMap.get('taskId'));
		this.taskService.getTaskById(this.taskId)
			.subscribe({
				next: (response: Task) => {
					this.taskFormControl['title'].setValue(response.title);
					this.taskFormControl['description'].setValue(response.description);
					this.taskFormControl['completedTask'].setValue(response.completedTask);
				},
				error: (error: HttpErrorResponse) => {
          this.errorHandlingService.redirectUnexpectedErrors(error);
        }
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
        	error: (error: HttpErrorResponse) => {
            this.errorHandlingService.redirectUnexpectedErrors(error);
          }
      	});
  	}

	save() {
    this.submitted = true;

		if(this.taskForm.valid) {
			if (this.newTask) { this.saveNewTask()}
			else { this.updateTask()}
		}
	}

	updateTask() {
		this.taskService.updateTask({
			title: this.taskFormControl['title'].value,
			description: this.taskFormControl['description'].value,
			id: this.taskId,
			completedTask: false
		}).subscribe({
			complete: ()=> {
				console.log(Response.toString);
				this.router.navigateByUrl(`/carecircle/${this.careCircleId}`)
			},
			error: (error: HttpErrorResponse)=> {
        this.errorHandlingService.redirectUnexpectedErrors(error);
    }
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
		this.taskService.deleteTask(this.taskId)
		.subscribe({
			complete: ()=> {
				console.log(Response.toString);
				this.router.navigateByUrl(`/carecircle/${this.careCircleId}`)
			},
			error: (error: HttpErrorResponse)=> {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      }
		})
	}
}
