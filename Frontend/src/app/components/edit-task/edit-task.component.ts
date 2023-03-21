import { ModalService } from './../../services/modal.service';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { FormGroup } from '@angular/forms';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Task } from 'src/app/interfaces/task';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent {

  @Input() editTaskForm: FormGroup;
  @Output() onUpdateTask: EventEmitter<Task> = new EventEmitter;
  @Output() onDeleteTask: EventEmitter<number> = new EventEmitter;

  taskId: number;
  taskTitle: string;
  submitted = false;
  faTrash = faTrash;

  constructor(protected modalService: ModalService) {}

  get editTaskFormControl(){
    return this.editTaskForm.controls;
  }

  setTaskToDelete(){
    this.modalService.close();
    this.modalService.open('deleteTask');
    this.taskId = this.editTaskFormControl['id'].value;
    this.taskTitle = this.editTaskFormControl['title'].value;
  }

  emitDeleteTask(taskId : number){
    this.taskId = taskId;
    this.onDeleteTask.emit(taskId);
  }

  emitUpdateTask(){
    const updatedTask = this.getUpdatedTaskFromForm();
    this.onUpdateTask.emit(updatedTask);
    this.resetForm();
  }

  private getUpdatedTaskFromForm(): Task{
    return {
      id: this.editTaskFormControl['id'].value,
      date: this.editTaskFormControl['date'].value,
      title: this.editTaskFormControl['title'].value,
      description: this.editTaskFormControl['description'].value,
      completedTask:this.editTaskFormControl['completedTask'].value
    };
  }

  private resetForm(){
    this.editTaskForm.reset();
    this.submitted = false;
    this.modalService.close();
  }

}
