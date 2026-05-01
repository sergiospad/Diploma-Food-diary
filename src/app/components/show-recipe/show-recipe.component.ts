import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {EDIT, FEED_ROOT, PROFILE, RECIPE, RECIPE_ID} from '../../util/roots';
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
import {MatButton, MatIconButton} from '@angular/material/button';
import {IngredientsShowRecipe} from './ingredients-show-recipe/ingredients-show-recipe';
import {EnergyInfoShowRecipe} from './energy-info-show-recipe/energy-info-show-recipe';
import {CookingStagesShowRecipe} from './cooking-stages-show-recipe/cooking-stages-show-recipe';
import {CommentsShowRecipe} from './comments-show-recipe/comments-show-recipe';
import {DateFormatter} from '../../util/date-formatter';

@Component({
  selector: 'app-show-recipe',
  imports: [
    MatIcon,
    MatButton,
    MatIconButton,
    IngredientsShowRecipe,
    EnergyInfoShowRecipe,
    CookingStagesShowRecipe,
    CommentsShowRecipe,
    DateFormatter
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
  private readonly cdr = inject(ChangeDetectorRef);

  recipeId = Number(this.activatedRoute.snapshot.paramMap.get(RECIPE_ID));
  recipe?:RecipeShowDTO;

  /** Обновление полей, влияющих на шаблон, после async (FileReader) — иначе NG0100. */
  private patchRecipeView(update: () => void): void {
    setTimeout(() => {
      update();
      this.cdr.detectChanges();
    }, 0);
  }

  ngOnInit(): void {
    this.recipeService.showRecipe(this.recipeId).pipe(
      tap((recipe) => {
        this.recipe = recipe;
      }),
      switchMap((recipe) =>
        this.imageService.getImage(recipe.illustrationID).pipe(
          switchMap((blob) => from(this.imageUpload.convertBlobToDataUrl(blob))),
          tap((url) => {
            this.patchRecipeView(() => {
              if (this.recipe) {
                this.recipe.illustration = url;
              }
            });
          }),
          map(() => recipe))),
      switchMap((recipe) =>
        recipe.authorName == this.tokenService.getUser()?.username
          ? of(null)
          : this.imageService.getImage(recipe.avatarID).pipe(
            switchMap((blob) => from(this.imageUpload.convertBlobToDataUrl(blob))
            ))),
      tap((url) => {
          if (url && this.recipe) {
            this.patchRecipeView(() => {
              this.recipe!.avatar = url;
            });
          } else {
            void this.avatarDB
              .getBlob()
              .then((blob) => this.imageUpload.convertBlobToDataUrl(blob))
              .then((avatarUrl) => {
                this.patchRecipeView(() => {
                  if (this.recipe) {
                    this.recipe.avatar = avatarUrl;
                  }
                });
              });
          }
        }
      ), catchError((error) => {

        this.router.navigate(['/', FEED_ROOT]).then(() => globalThis.location.reload());
        this.notificationService.showSnackBar('Рецепт не найден!');
        return EMPTY;
      }),
    ).subscribe();

  }

  protected editRecipe() {
    this.router.navigate(['/', RECIPE, EDIT, this.recipeId])
      .then(()=>globalThis.location.reload());
  }

  protected ableToEdit(){
    return this.recipe?.authorName == this.tokenService.getUser()?.username;
  }

  protected onTagClick(id: number) {
    return this.router.navigate(['/', FEED_ROOT], { queryParams: { tags:[id]}})
  }

  protected goToProfile(): void {
    void this.router.navigate(['/', FEED_ROOT], { queryParams: {authorName: this.recipe?.authorName}});
  }
}
