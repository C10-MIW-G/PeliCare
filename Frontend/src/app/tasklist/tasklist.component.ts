import { Component } from '@angular/core';
import { Task } from '../task';

@Component({
  selector: 'app-tasklist',
  templateUrl: './tasklist.component.html',
  styleUrls: ['./tasklist.component.css']
})
export class TasklistComponent {
    tasklist: Task[] = [
      { 
        id: 123,
        title: "Poekie",
        description: "kat voeren"
      },
      { 
        id: 456,
        title: "Wodan",
        description: "hond uitlaten"
      }
            
    ]
}
