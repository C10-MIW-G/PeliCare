import { ModalService } from './../../services/modal.service';
import { Component, Input, ViewEncapsulation, ElementRef, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ModalComponent implements OnInit, OnDestroy{
  @Input() id?: string;
  isOpen = false;
  private element: any;

  constructor(private modalService: ModalService, el: ElementRef){
    this.element = el.nativeElement;
  }

  ngOnInit(){
    this.modalService.add(this);

    document.body.appendChild(this.element);

    this.element.addEventListener('click', (el: any) => {
      if (el.target.className === 'modal') {
        this.close();
      }
    }
    );
  }

  ngOnDestroy(){
    this.modalService.remove(this);

    this.element.remove();
  }

  open(){
    this.element.style.display = 'block';
    document.body.classList.add('modal-open');
    this.isOpen = true;
  }

  close() {
    this.element.style.display = 'none';
    document.body.classList.remove('modal-open');
    this.isOpen = false;
  }
}
