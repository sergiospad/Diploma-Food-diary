import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FEED_ROOT, RECIPE_ID} from '../../util/roots';
import RecipeShowDTO from '../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeService from '../../service/recipe.service';
import {NotificationService} from '../../security/notification-service';
import {ImageUploadService} from '../../image_services/image-upload.service';
import ImageModelService from '../../service/recipe_resource/image-model.service';
import {catchError, map, switchMap, tap} from 'rxjs/operators';
import {EMPTY, from, of} from 'rxjs';
import {TokenStorageService} from '../../security/token-storage.service';
import AvatarIndexedDb from '../../image_services/avatar-indexed.db';
import {MatIcon} from '@angular/material/icon';
import {MatButton} from '@angular/material/button';
import {IngredientsShowRecipe} from './ingredients-show-recipe/ingredients-show-recipe';

@Component({
  selector: 'app-show-recipe',
  imports: [
    MatIcon,
    MatButton,
    IngredientsShowRecipe
  ],
  templateUrl: './show-recipe.component.html',
  styleUrl: './show-recipe.component.css',
})
export class ShowRecipeComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly tokenService = inject(TokenStorageService);
  private readonly router = inject(Router);
  private readonly recipeService = inject(RecipeService);
  private readonly notificationService = inject(NotificationService);
  private readonly imageUpload = inject(ImageUploadService);
  private readonly imageService = inject(ImageModelService);
  private readonly avatarDB = inject(AvatarIndexedDb);

  recipeId = Number(this.activatedRoute.snapshot.paramMap.get(RECIPE_ID));
  recipe?:RecipeShowDTO;

  ngOnInit(): void {
    this.recipeService.showRecipe(this.recipeId).pipe(
      tap((recipe) => {
        console.dir(recipe);
        this.recipe = recipe;
      }),
      switchMap((recipe) =>
        this.imageService.getImage(recipe.illustrationID).pipe(
          switchMap((blob) => from(this.imageUpload.convertBlobToDataUrl(blob))),
          tap((url) => {
            this.recipe!.illustration = url;
          }),
          map(() => recipe))),
      switchMap((recipe) =>
        recipe.authorName == this.tokenService.getUser()?.username
          ? of(null)
          : this.imageService.getImage(recipe.avatarID).pipe(
            switchMap((blob) => from(this.imageUpload.convertBlobToDataUrl(blob))
            ))),
      tap((url) => {
          if (url) {
            this.recipe!.avatar = url;
          }else{
            this.avatarDB.getBlob()
              .then((blob)=> this.imageUpload.convertBlobToDataUrl(blob))
              .then((url)=> this.recipe!.avatar = url)
          }
        }
      ), catchError((error) => {

        this.router.navigate(['/', FEED_ROOT]).then(() => globalThis.location.reload());
        this.notificationService.showSnackBar('Рецепт не найден!');
        console.log(error);
        return EMPTY;
      }),
    ).subscribe();

  }

  protected editRecipe() {
    //TODO добавить edit recipe
  }

  protected ableToEdit(){
    return this.recipe?.authorName == this.tokenService.getUser()?.username;
  }
}
