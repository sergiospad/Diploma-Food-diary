import {ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, RouterStateSnapshot} from '@angular/router';
import {inject, Injectable} from '@angular/core';
import {RECIPE_ID} from '../util/roots';
import RecipeService from '../service/recipe.service';
import {TokenStorageService} from './token-storage.service';
import {map} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class RecipeEditGuardService implements CanActivate {
  recipeService = inject(RecipeService);
  tokenService = inject(TokenStorageService);
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    const id = Number.parseInt(route.paramMap.get(RECIPE_ID)!);
    return this.recipeService.findAuthorByRecipe(id).pipe(map(authorID=>{
      const userID = this.tokenService.getUser()?.id;
      if(!userID) return false
      return authorID==userID;
    }));
  }
}
