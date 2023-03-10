import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NewTask } from 'src/app/interfaces/new-task';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit{

  @Input() careCircleId: number;
  @Output() onAddTask: EventEmitter<NewTask> = new EventEmitter();
  addTaskForm: FormGroup;
  submitted = false;

  constructor (
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.addTaskForm = this.fb.group({
      date: [],
      title: ['', Validators.required],
      description: ['', Validators.required],
      completedTask: [''],
    });
  }

  submitTask(careCircleId: number){
    this.submitted = true;
    if(this.addTaskForm.valid){
      this.addTask(careCircleId);
    }
  }

  addTask(careCircleId : number){

    const newTask = this.getNewTaskFromForm(careCircleId);

    this.onAddTask.emit(newTask);
    this.resetForm();
  }

  private getNewTaskFromForm(careCircleId: number): NewTask{
    return {
      date: this.taskFormControl['date'].value,
      title: this.taskFormControl['title'].value,
      description: this.taskFormControl['description'].value,
      careCircleId: careCircleId
    }
  }

  private resetForm(){
    this.addTaskForm.reset();
    this.submitted = false;
  }

  get taskFormControl(){
    return this.addTaskForm.controls;
  }
}
