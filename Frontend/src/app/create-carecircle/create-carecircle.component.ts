import { CareCircleService } from './../care-circle.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-carecircle',
  templateUrl: './create-carecircle.component.html',
  styleUrls: ['./create-carecircle.component.css']
})
export class CreateCarecircleComponent {
  form: FormGroup;


  constructor(private fb:FormBuilder,
    private careCircleService: CareCircleService,
    private router: Router) {
      this.form = this.fb.group({
        name: ['',Validators.required],
      });
    }

  create() {
    const val = this.form.value;

    if (val.name) {
      this.careCircleService.createNewCareCircle(val.name).subscribe({
        complete: ()=> {
          console.log("Care Circle is created");
          this.router.navigateByUrl('/carecircles');},
          error: ()=> {alert( "name is already in use");
        }
      })
    }
  }

}
