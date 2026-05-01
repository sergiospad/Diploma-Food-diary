import { Routes } from '@angular/router';
import LoginComponent from './components/auth/login/login.component';
import RegisterComponent from './components/auth/register/register.component';
import { FeedComponent } from './components/feed/feed';
import {ADD_RECIPE, CATEGORY, EDIT_RECIPE, FEED_ROOT, LOGIN, PROFILE, RECIPE, RECIPE_ID, REGISTER} from './util/roots';
import AddRecipeComponent from './components/add-recipe/add-recipe';
import {ShowRecipeComponent} from './components/show-recipe/show-recipe.component';
import {EditRecipeComponent} from './components/edit-recipe/edit-recipe';
import {RecipeEditGuardService} from './security/recipe-edit-guard.service';
import {UserProfile} from './components/user-profile/user-profile';
import {Categories} from './components/categories/categories';

export const routes: Routes = [
  { path: LOGIN, component: LoginComponent },
  { path: REGISTER, component: RegisterComponent },
  { path: FEED_ROOT, component: FeedComponent },
  { path:ADD_RECIPE, component:AddRecipeComponent },
  { path: RECIPE+'/:'+RECIPE_ID, component:ShowRecipeComponent},
  { path: PROFILE, component:UserProfile},
  {path: CATEGORY, component: Categories},
  { path: EDIT_RECIPE+"/:"+RECIPE_ID, component: EditRecipeComponent,
    canActivate:[RecipeEditGuardService],
  },
  { path: '', redirectTo: FEED_ROOT, pathMatch: 'full' },
];
