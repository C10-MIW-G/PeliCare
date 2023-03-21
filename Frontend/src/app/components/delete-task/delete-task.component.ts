import { ModalService } from './../../services/modal.service';
import { Component, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-delete-task',
  templateUrl: './delete-task.component.html',
  styleUrls: ['./delete-task.component.css']
})

export class DeleteTaskComponent {
  @Output() onDeleteTask: EventEmitter<number> = new EventEmitter;
  @Input() taskId: number;
  @Input() taskTitle: string;

  constructor(protected modalService: ModalService){}

  deleteTask(taskId: number){
    this.taskId = taskId;
    this.modalService.close();
    this.onDeleteTask.emit(taskId);
  }

  goBack(){
    this.modalService.close();
    this.modalService.open('editTask');
  }
}
