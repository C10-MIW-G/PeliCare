import { NotFoundComponent } from './components/not-found/not-found.component';
import { ErrorComponent } from './components/error/error.component';
import { CarecircleMembersComponent } from './components/carecircle-members/carecircle-members.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { CreateCarecircleComponent } from './components/create-carecircle/create-carecircle.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarecirclelistComponent } from './components/carecircle-list/carecircle-list.component';
import { CareCircleComponent } from './components/carecircle/carecircle.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { TaskComponent } from './components/task/task.component';
import { HomeComponent } from './components/home/home.component';

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
  { path: '**', component: NotFoundComponent },
  { path: 'account/changepassword', component: ChangePasswordComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
