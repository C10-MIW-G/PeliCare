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

    public saveNewTaskData( newTask: NewTask): Observable<NewTask> {
        return this.http.post<NewTask> (`${this.apiBackendUrl}/task/new`, {
            title: newTask.title,
            description: newTask.description,
            careCircleId: newTask.careCircleId
            });
    }


    public getTaskById(id: Number): Observable<Task> {
        return this.http.get<Task>(`${this.apiBackendUrl}/task/get/${id}`);
    }

    public updateTask(task: Task): Observable<Task> {
        return this.http.patch<Task>(`${this.apiBackendUrl}/task/patch`, {
            title: task.title,
            description: task.description,
            id: task.id
        });
    }

    public setTaskToComplete( task: Task): Observable<Task> {
        return this.http.patch<Task>(`${this.apiBackendUrl}/task/complete`, {
          id: task.id,
          title: task.title,
          description: task.description,
          completedTask: task.completedTask
        });
    }
}
    



