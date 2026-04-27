import { Routes } from '@angular/router';
import LoginComponent from './components/auth/login/login.component';
import RegisterComponent from './components/auth/register/register.component';
import { FeedComponent } from './components/feed/feed';
import {ADD_RECIPE, FEED_ROOT, LOGIN, REGISTER} from './util/roots';
import {AddRecipeComponent} from './components/add-recipe/add-recipe';

export const routes: Routes = [
  { path: LOGIN, component: LoginComponent },
  { path: REGISTER, component: RegisterComponent },
  { path: FEED_ROOT, component: FeedComponent },
  { path:ADD_RECIPE, component:AddRecipeComponent },
  { path: '', redirectTo: FEED_ROOT, pathMatch: 'full' },
];
