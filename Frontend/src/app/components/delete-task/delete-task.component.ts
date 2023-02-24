import { Component, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-delete-task',
  templateUrl: './delete-task.component.html',
  styleUrls: ['./delete-task.component.css']
})
export class DeleteTaskComponent {
  @Output() onDeleteTask: EventEmitter<number> = new EventEmitter;
  @Input() taskId: number;

  deleteTask(taskId: number){
    this.taskId = taskId;
    this.onDeleteTask.emit(taskId);
  }
}
