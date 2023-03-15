import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService as AuthGuard } from './services/auth-guard.service';

import { NotFoundComponent } from './components/not-found/not-found.component';
import { ErrorComponent } from './components/error/error.component';
import { CarecircleMembersComponent } from './components/carecircle-members/carecircle-members.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { CreateCarecircleComponent } from './components/create-carecircle/create-carecircle.component';
import { CarecirclelistComponent } from './components/carecircle-list/carecircle-list.component';
import { CareCircleComponent } from './components/carecircle/carecircle.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { TaskComponent } from './components/task/task.component';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'carecircles', component: CarecirclelistComponent, canActivate: [AuthGuard] },
  { path: 'carecircle/:id', component: CareCircleComponent, canActivate: [AuthGuard] },
  { path: 'account/create', component: CreateAccountComponent },
  { path: 'account/signin', component: SignInComponent },
  { path: 'carecircles/create', component: CreateCarecircleComponent, canActivate: [AuthGuard] },
  { path: 'task/create/:circleId', component: TaskComponent, canActivate: [AuthGuard] },
  { path: 'task/edit/:taskId/:circleId', component: TaskComponent, canActivate: [AuthGuard] },
  { path: 'carecircle/:id/members', component: CarecircleMembersComponent, canActivate: [AuthGuard]},
  { path: 'account/settings', component: UserSettingsComponent, canActivate: [AuthGuard]},
  { path: 'account/changepassword', component: ChangePasswordComponent, canActivate: [AuthGuard] },
  { path: 'account/profile', component: UserProfileComponent, canActivate: [AuthGuard]},
  { path: 'error', component: ErrorComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
