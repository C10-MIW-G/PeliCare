import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService as AuthGuard } from './services/auth-guard.service';

import { NotFoundComponent } from './components/not-found/not-found.component';
import { ErrorComponent } from './components/error/error.component';
import { CarecircleMembersComponent } from './components/carecircle-members/carecircle-members.component';
import { CarecirclelistComponent } from './components/carecircle-list/carecircle-list.component';
import { CareCircleComponent } from './components/carecircle/carecircle.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { HomeComponent } from './components/home/home.component';
import { CarecircleOverviewComponent } from './components/carecircle-overview/carecircle-overview.component';

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'carecircles', component: CarecirclelistComponent, canActivate: [AuthGuard] },
  { path: 'carecircle/:id/overview', component: CarecircleOverviewComponent, canActivate: [AuthGuard] },
  { path: 'carecircle/:id', component: CareCircleComponent, canActivate: [AuthGuard] },
  { path: 'account/create', component: CreateAccountComponent },
  { path: 'account/signin', component: SignInComponent },
  { path: 'carecircle/:id/members', component: CarecircleMembersComponent, canActivate: [AuthGuard]},
  { path: 'account/settings', component: UserSettingsComponent, canActivate: [AuthGuard]},
  { path: 'error', component: ErrorComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
