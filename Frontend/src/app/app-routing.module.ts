<<<<<<< HEAD
<<<<<<< HEAD
import { NotFoundComponent } from './not-found/not-found.component';
import { ErrorComponent } from './error/error.component';
import { CarecircleMembersComponent } from './carecircle-members/carecircle-members.component';
=======
import { ChangePasswordComponent } from './change-password/change-password.component';
>>>>>>> 1a584a9296667fcba322f3bcf1136936e99f1dc1
=======
import { NotFoundComponent } from './not-found/not-found.component';
import { ErrorComponent } from './error/error.component';
import { CarecircleMembersComponent } from './carecircle-members/carecircle-members.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
>>>>>>> cfb42ba20134564efdcb19ec3f50395a560c1892
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
<<<<<<< HEAD
=======
>>>>>>> cfb42ba20134564efdcb19ec3f50395a560c1892
  { path: 'carecircle/:id/members', component: CarecircleMembersComponent},
  { path: 'error', component: ErrorComponent },
  { path: 'account/changepassword', component: ChangePasswordComponent },
  { path: '**', component: NotFoundComponent }
<<<<<<< HEAD
=======
  { path: 'account/changepassword', component: ChangePasswordComponent }
>>>>>>> 1a584a9296667fcba322f3bcf1136936e99f1dc1
=======
>>>>>>> cfb42ba20134564efdcb19ec3f50395a560c1892
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
