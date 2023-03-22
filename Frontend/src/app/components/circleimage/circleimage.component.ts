import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ImageService } from 'src/app/services/image.service';
import { ErrorHandlingService } from 'src/app/services/error-handling.service';

/*
plaats in template van oudercomponent CareCircle,
bij aanmaken regelt deze component zelf de juiste inhoud, met backend-api calls
één plek om deze logica op te slaan
*/
@Component({
  selector: 'app-circleimage',
  templateUrl: './circleimage.component.html',
  styleUrls: ['./circleimage.component.css']
})
export class CircleimageComponent implements OnInit, OnChanges{

  constructor( private imageService: ImageService,
                private errorHandlingService: ErrorHandlingService) {} 
	ngOnChanges(changes: SimpleChanges): void {
		// refresh the data		
		this.ngOnInit();
	}

  @Input() imagefilename: string;

  public imageBlobUrl: string | ArrayBuffer | null;

  ngOnInit(): void {
    if (this.imagefilename 
			&& 
			this.imagefilename != 'no file selected') {
			this.imageService.download(this.imagefilename)
				.subscribe({
					next: (response) => {
						let reader = new FileReader();
						reader.addEventListener("load", () => {
							this.imageBlobUrl = reader.result;
						}, false);
						reader.readAsDataURL(response);
					},
					error: (error: HttpErrorResponse) => {
						this.errorHandlingService.redirectUnexpectedErrors(error);
					}
				});
		} else {
			this.imageBlobUrl = "../../../assets/images/PeliCare-zwart.png"
		}
  } 
}
