import { User } from './../user';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CareCircleService } from '../care-circle.service';
import { CareCircle } from '../carecircle';
import { TaskService } from '../services/task.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-carecircle-users',
  templateUrl: './carecircle-users.component.html',
  styleUrls: ['./carecircle-users.component.css']
})
export class CarecircleUsersComponent implements OnInit {
  public careCircle: CareCircle;
  public user: User;
  form: FormGroup;

    constructor (
      private route: ActivatedRoute,
      private careCircleService: CareCircleService,
      private fb:FormBuilder,
      private router: Router) {
      this.form = this.fb.group({
        email: [''],
      });
    }

  ngOnInit(): void {
    this.getCareCircle;
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

  onSubmitAdd(): void {
    this.careCircleService.addUserToCareCircle(this.form.value.email).subscribe;
  }

}
