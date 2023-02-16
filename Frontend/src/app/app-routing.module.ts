import { NotFoundComponent } from './not-found/not-found.component';
import { ErrorComponent } from './error/error.component';
import { CarecircleMembersComponent } from './carecircle-members/carecircle-members.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { CreateCarecircleComponent } from './create-carecircle/create-carecircle.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarecirclelistComponent } from './carecirclelist/carecirclelist.component';
import { CareCircleComponent } from './careCircle/careCircle.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { TaskComponent } from './task/task.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'carecircles', component: CarecirclelistComponent },
  { path: 'carecircle/:id', component: CareCircleComponent },
  { path: 'account/create', component: CreateAccountComponent },
  { path: 'account/signin', component: SignInComponent },
  { path: 'carecircles/create', component: CreateCarecircleComponent },
  { path: 'task/create/:circleId', component: TaskComponent },
  { path: 'task/edit/:taskId/:circleId', component: TaskComponent },
  { path: 'carecircle/:id/members', component: CarecircleMembersComponent},
  { path: 'error', component: ErrorComponent },
  { path: 'account/changepassword', component: ChangePasswordComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
