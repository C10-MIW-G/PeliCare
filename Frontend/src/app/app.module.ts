import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CareCircleService } from './services/care-circle.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RECAPTCHA_SETTINGS, RecaptchaFormsModule, RecaptchaModule, RecaptchaSettings } from 'ng-recaptcha';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CarecirclelistComponent } from './components/carecircle-list/carecircle-list.component';
import { CareCircleComponent } from './components/carecircle/carecircle.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import { InterceptorService } from './services/interceptor.service';
import { CreateCarecircleComponent } from './components/create-carecircle/create-carecircle.component';
import { environment } from 'src/environments/environment.development';
import { TaskComponent } from './components/task/task.component';
import { CarecircleMembersComponent } from './components/carecircle-members/carecircle-members.component';
import { ErrorComponent } from './components/error/error.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomeComponent } from './components/home/home.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';

@NgModule({
  declarations: [
    AppComponent,
    CarecirclelistComponent,
    CareCircleComponent,
    SignInComponent,
    CreateAccountComponent,
    CreateCarecircleComponent,
    TaskComponent,
    CarecircleMembersComponent,
    ErrorComponent,
    NotFoundComponent,
    HomeComponent,
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
