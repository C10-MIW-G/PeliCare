import { ModalService } from './../../services/modal.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CareCircleService } from 'src/app/services/care-circle.service';
import { ErrorHandlingService } from 'src/app/services/error-handling.service';

@Component({
  selector: 'add-carecircle',
  templateUrl: './add-carecircle.component.html',
  styleUrls: ['./add-carecircle.component.css']
})
export class AddCarecircleComponent {

  @Output() onAddCircle: EventEmitter<string> = new EventEmitter;
  createCircleForm: FormGroup;
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private careCircleService: CareCircleService,
    private errorHandlingService: ErrorHandlingService,
    protected modalService: ModalService
  ) {}

  ngOnInit() {
    this.createCircleForm = this.fb.group({
      name: ['', Validators.required],
    });
  }

  get createCircleFormControl() {
    return this.createCircleForm.controls;
  }

  create() {
    this.submitted = true;

    if (this.createCircleForm.valid) {
      this.careCircleService
        .createNewCareCircle(this.createCircleFormControl['name'].value)
        .subscribe({
          complete: () => {
            this.modalService.close();
            this.resetForm();
            this.onAddCircle.emit();
          },
          error: (error: HttpErrorResponse) => {
            this.errorHandlingService.redirectUnexpectedErrors(error);
          },
        });
    }
  }

  private resetForm() {
    this.createCircleForm.reset();
    this.submitted = false;
  }
}
