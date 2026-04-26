import { Routes } from '@angular/router';
import LoginComponent from './components/auth/login/login.component';
import RegisterComponent from './components/auth/register/register.component';
import { FeedComponent } from './components/feed/feed';
import { FEED_ROOT, LOGIN, REGISTER } from './util/roots';

export const routes: Routes = [
  { path: LOGIN, component: LoginComponent },
  { path: REGISTER, component: RegisterComponent },
  { path: FEED_ROOT, component: FeedComponent },
  { path: '', redirectTo: FEED_ROOT, pathMatch: 'full' },
];
