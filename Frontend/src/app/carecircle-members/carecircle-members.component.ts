import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CareCircleService } from '../care-circle.service';

@Component({
  selector: 'app-carecircle-members',
  templateUrl: './carecircle-members.component.html',
  styleUrls: ['./carecircle-members.component.css']
})
export class CarecircleMembersComponent {
  form: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private fb:FormBuilder,
    private careCircleService: CareCircleService){
      this.form = this.fb.group({
        email: ['',Validators.required],
      })
    }

    addUserToCareCircle(){
      const id = Number(this.route.snapshot.paramMap.get('id'));
      const val = this.form.value;
      this.careCircleService.addUserToCareCircle(id, val.email).subscribe();
    }
}
