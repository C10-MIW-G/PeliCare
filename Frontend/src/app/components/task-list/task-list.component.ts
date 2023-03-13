import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ErrorHandlingService } from 'src/app/services/error-handling.service';
import { TaskService } from './../../services/task.service';
import { Component, Input, OnInit } from '@angular/core';
import { Task } from 'src/app/interfaces/task';
import {
  faCheckCircle,
  faCheckSquare,
  faPencil,
  faSquare,
} from '@fortawesome/free-solid-svg-icons';
import { HttpErrorResponse } from '@angular/common/http';
import { NewTask } from 'src/app/interfaces/new-task';
import { formatDate } from '@angular/common';


@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css'],
})
export class TaskListComponent implements OnInit {
  @Input() careCircleId: number;

  taskList: Task[];
  editTaskForm: FormGroup;
  faCheckCircle = faCheckCircle;
  faPencil = faPencil;
  faCheckSquare = faCheckSquare;
  faSquare = faSquare;

  constructor(
    private taskService: TaskService,
    private errorHandlingService: ErrorHandlingService,
    private fb: FormBuilder
  ) { }



  ngOnInit() {
    this.editTaskForm = this.fb.group({
      id: [0],
      date: [''],
      title: ['', Validators.required],
      description: ['', Validators.required],
      completedTask: [''],
    });

    this.getTasks(this.careCircleId);
  }

  public getTasks(careCircleId: number): void {
    this.taskService.getTasksByCareCircle(careCircleId).subscribe({
      next: (response: Task[]) => {
        this.taskList = response;
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }

  addTask(newTask: NewTask) {
    this.taskService.saveNewTaskData(newTask).subscribe({
      complete: () => {
        this.getTasks(this.careCircleId);
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }

  updateTask(task: Task) {
    this.taskService.updateTask(task).subscribe({
      complete: () => {
        this.getTasks(this.careCircleId);
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }

  toggleTaskCompleted(task: Task) {
    this.taskService.setTaskToComplete(task).subscribe({
      complete: () => {
        this.getTasks(this.careCircleId);
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }

  deleteTask(taskId: number) {
    this.taskService.deleteTask(taskId).subscribe({
      complete: () => {
        this.getTasks(this.careCircleId);
      },
      error: (error: HttpErrorResponse) => {
        this.errorHandlingService.redirectUnexpectedErrors(error);
      },
    });
  }

  undoTask(task: Task) {
    task.completedTask = false;
    this.toggleTaskCompleted(task);
  }

  isInThePast(date: Date): boolean {
    const today = new Date();
    return (date.getDate() < today.getDate());
  }

  completeTask(task: Task) {
    if (this.isInThePast(task.date)) {
      task.completedTask = true;
      this.toggleTaskCompleted(task);
    }

  }


  public fillEditTaskForm(task: Task): void {
    this.editTaskForm.controls['id'].setValue(task.id);
    this.editTaskForm.controls['date'].setValue(task.date);
    this.editTaskForm.controls['title'].setValue(task.title);
    this.editTaskForm.controls['description'].setValue(task.description);
    this.editTaskForm.controls['completedTask'].setValue(task.completedTask);
  }
}
