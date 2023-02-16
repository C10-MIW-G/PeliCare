import { ApiError } from './api-error';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit{

  apiError: ApiError;

  constructor(private router: Router){
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as ApiError;
    this.apiError = state;
  }

  ngOnInit(): void {

  }

}
