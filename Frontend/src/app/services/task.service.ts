// communicating with backend API concerning Task information

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { NewTask } from "../newtask";
import { Task } from "../task";

@Injectable({providedIn: 'root'})
export class TaskService {
    private apiBackendUrl='http://localhost:8080';
    constructor(private http: HttpClient) { }    

    // public saveNewTaskObject(task: Task, careCircleId: number): Observable<Task> {
    //     return this.http.post<Task> (`${this.apiBackendUrl}/task/new`, {task, careCircleId });
    // }

   
    public saveNewTaskData( newTask: NewTask): Observable<NewTask> {
        return this.http.post<NewTask> (`${this.apiBackendUrl}/task/new`, {
            title: newTask.title,
            description: newTask.description,
            careCircleId: newTask.careCircleId
            });
    }


    

}