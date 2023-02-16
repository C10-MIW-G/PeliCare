import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CareCircleService } from './care-circle.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RECAPTCHA_SETTINGS, RecaptchaFormsModule, RecaptchaModule, RecaptchaSettings } from 'ng-recaptcha';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CarecirclelistComponent } from './carecirclelist/carecirclelist.component';
import { CareCircleComponent } from './careCircle/careCircle.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { InterceptorService } from './services/interceptor.service';
import { CreateCarecircleComponent } from './create-carecircle/create-carecircle.component';
import { environment } from 'src/environments/environment.development';
import { TaskComponent } from './task/task.component';
import { ChangePasswordComponent } from './change-password/change-password.component';


@NgModule({
  declarations: [
    AppComponent,
    CarecirclelistComponent,
    CareCircleComponent,
    SignInComponent,
    CreateAccountComponent,
    CreateCarecircleComponent,
    TaskComponent,
    ChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RecaptchaModule,
    RecaptchaFormsModule,
    
   
  ],
  providers: [CareCircleService, {
    provide: HTTP_INTERCEPTORS,
    useClass: InterceptorService,
    multi: true
  },
  {
    provide: RECAPTCHA_SETTINGS,
    useValue: {
      siteKey: environment.recaptcha.siteKey,
    } as RecaptchaSettings,
}],
  bootstrap: [AppComponent],
  
})
export class AppModule { }
