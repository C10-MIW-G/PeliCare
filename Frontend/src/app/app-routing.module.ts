<<<<<<< HEAD
import { NotFoundComponent } from './not-found/not-found.component';
import { ErrorComponent } from './error/error.component';
import { CarecircleMembersComponent } from './carecircle-members/carecircle-members.component';
=======
import { ChangePasswordComponent } from './change-password/change-password.component';
>>>>>>> 1a584a9296667fcba322f3bcf1136936e99f1dc1
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
<<<<<<< HEAD
  { path: 'carecircle/:id/members', component: CarecircleMembersComponent},
  { path: 'error', component: ErrorComponent },
  { path: '**', component: NotFoundComponent }
=======
  { path: 'account/changepassword', component: ChangePasswordComponent }
>>>>>>> 1a584a9296667fcba322f3bcf1136936e99f1dc1
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
