import { CareCircleService } from './care-circle.service';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CarecirclelistComponent } from './carecirclelist/carecirclelist.component';
import { CareCircleComponent } from './careCircle/careCircle.component';
import { RouterModule, Routes } from '@angular/router';
import { SignInComponent } from './sign-in/sign-in.component';
import { CreateAccountComponent } from './create-account/create-account.component';



@NgModule({
  declarations: [
    AppComponent,
    CarecirclelistComponent,
    CareCircleComponent,
    SignInComponent,
    CreateAccountComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule    
  ],
  providers: [CareCircleService],
  bootstrap: [AppComponent]
})
export class AppModule { }
