import { TaskService } from './../services/task.service';
import { Task } from './../task';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircleService } from '../care-circle.service';
import { CareCircle } from '../carecircle';

@Component({
  selector: 'app-carecircle',
  templateUrl: './careCircle.component.html',
  styleUrls: ['./careCircle.component.css']
})
export class CareCircleComponent implements OnInit {

	public careCircle: CareCircle;
	
    constructor (
      private route: ActivatedRoute,
      private careCircleService: CareCircleService,
      private taskservice: TaskService
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
			   console.log(this.careCircle);
			 },
			error: (error: HttpErrorResponse) => {
			   alert(error.message);
			 }
		   });
	}

  complete(task: Task) {
    task.completedTask = true;
    this.taskservice.setTaskToComplete(task).subscribe();
  }
}
