import { CarecircleUsersComponent } from './carecircle-users/carecircle-users.component';
import { CreateCarecircleComponent } from './create-carecircle/create-carecircle.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarecirclelistComponent } from './carecirclelist/carecirclelist.component';
import { CareCircleComponent } from './careCircle/careCircle.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { TaskComponent } from './task/task.component';


const routes: Routes = [
  { path: 'carecircles', component: CarecirclelistComponent },
  { path: 'carecircle/:id', component: CareCircleComponent },
  { path: 'account/create', component: CreateAccountComponent },
  { path: 'account/signin', component: SignInComponent },
  { path: 'carecircles/create', component: CreateCarecircleComponent },
  { path: 'task/create/:circleId', component: TaskComponent },
  { path: 'task/edit/:taskId/:circleId', component: TaskComponent },
  { path: 'carecircle/:id/members', component: CarecircleUsersComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
