import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { NewTask } from '../newtask';
import { TaskService } from '../services/task.service';
import { Task } from '../task';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit{

  public task: Task;
  public id: Number;
  public title: string;
  public description: string;

  public careCircleId: number;
  constructor (
    private route: ActivatedRoute,
    private taskservice: TaskService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // vind de CareCircleId
    this.careCircleId = Number(this.route.snapshot.paramMap.get('circleId'));
  }

  saveNewTask() {

    if (this.title && this.description) {      

      // let newTask: NewTask = {
      //         'title': this.title,
      //         'description': this.description,
      //         'careCircleId': this.careCircleId
      // }

      this.taskservice.saveNewTaskData({
        title: this.title,
        description: this.description,
        careCircleId: this.careCircleId
      }) 
      .subscribe({
        complete: ()=> {
          console.log(Response.toString);
          this.router.navigateByUrl(`/carecircle/${this.careCircleId}`)},
          error: ()=> {alert( "something went wrong");
        }
      });

      }  
    }  
}
