import { ModalService } from './../../services/modal.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
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
  selectedFile?: File;
	filenames: string[] = [];
	formData!: FormData;

  // used to remove the file selection from form, for next use 
  @ViewChild('myFileSelect')
  myFileSelector: ElementRef

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

  fileSelected(event: any) {
		this.selectedFile = event.target.files[0];
	}

  create() {    

    this.submitted = true;

		if (this.createCircleForm.valid) {
			this.formData = new FormData();
			this.formData.append('carecirclename', this.createCircleFormControl['name'].value);
			if(this.selectedFile) {
				this.formData.append('files', this.selectedFile);
			} else {
				this.formData.append('files', new File( [], 'no file selected'));
			}

			this.careCircleService.uploadFile(this.formData)
			.subscribe({
				complete: () => {
					console.log('image is uploaded');
          this.filenames = [];
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
    this.myFileSelector.nativeElement.value = "";    
  }
}
