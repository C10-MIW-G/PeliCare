import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CareCircleService } from '../care-circle.service';
import { CareCircle } from '../carecircle';
import { Task } from '../task';

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

    ) {}

	ngOnInit(): void {
		this.getCareCircle();
	}

	getCareCircle(): void {

		const id = Number(this.route.snapshot.paramMap.get('id'));
		this.careCircleService.getCareCircleById(id)
		.subscribe(careCircle => this.careCircle = careCircle);
	}
}
