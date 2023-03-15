import { TooltipModule } from './components/ui/tooltip/tooltip.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CareCircleService } from './services/care-circle.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RECAPTCHA_SETTINGS, RecaptchaFormsModule, RecaptchaModule, RecaptchaSettings } from 'ng-recaptcha';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CarecirclelistComponent } from './components/carecircle-list/carecircle-list.component';
import { CareCircleComponent } from './components/carecircle/carecircle.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import { InterceptorService } from './services/interceptor.service';
import { CreateCarecircleComponent } from './components/create-carecircle/create-carecircle.component';
import { environment } from 'src/environments/environment.development';
import { CarecircleMembersComponent } from './components/carecircle-members/carecircle-members.component';
import { ErrorComponent } from './components/error/error.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomeComponent } from './components/home/home.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { TaskListComponent } from './components/task-list/task-list.component';
import { AddTaskComponent } from './components/add-task/add-task.component';
import { EditTaskComponent } from './components/edit-task/edit-task.component';
import { DeleteTaskComponent } from './components/delete-task/delete-task.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { DeleteUserComponent } from './components/delete-user/delete-user.component';
import { ModalComponent } from './components/modal/modal.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { CarecircleInfoWidgetComponent } from './components/carecircle-info-widget/carecircle-info-widget.component';
import { AddCarecircleComponent } from './components/add-carecircle/add-carecircle.component';

@NgModule({
  declarations: [
    AppComponent,
    CarecirclelistComponent,
    CareCircleComponent,
    SignInComponent,
    CreateAccountComponent,
    CreateCarecircleComponent,
    CarecircleMembersComponent,
    ErrorComponent,
    NotFoundComponent,
    HomeComponent,
    ChangePasswordComponent,
    TaskListComponent,
    AddTaskComponent,
    EditTaskComponent,
    DeleteTaskComponent,
    UserSettingsComponent,
    DeleteUserComponent,
    ModalComponent,
    UserProfileComponent,
    CarecircleInfoWidgetComponent,
    AddCarecircleComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RecaptchaModule,
    RecaptchaFormsModule,
    FontAwesomeModule,
    TooltipModule
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
