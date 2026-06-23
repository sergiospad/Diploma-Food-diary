import {Component, inject, OnInit, signal} from '@angular/core';
import {RECIPE, RECIPE_ID} from '../../util/roots';
import {ActivatedRoute, Router} from '@angular/router';
import RecipeService from '../../service/recipe.service';
import RecipeShowDTO from '../../DTO/entity_dto/recipe/recipe-show.dto';
import {MatButton} from '@angular/material/button';
import {EditRecipeMainInfoSection} from './edit-recipe-main-info-section/edit-recipe-main-info-section';
import RecipeEditProjection from '../../DTO/entity_dto/recipe/recipe-edit-projection';
import {catchError, map, switchMap, tap} from 'rxjs/operators';
import ImageModelService from '../../service/recipe_resource/image-model.service';
import {ImageUploadService} from '../../image_services/image-upload.service';
import {from, of} from 'rxjs';
import {EditRecipeIngredientSection} from './edit-recipe-ingredient-section/edit-recipe-ingredient-section';
import {EditRecipeCookingStages} from './edit-recipe-cooking-stages/edit-recipe-cooking-stages';
import {EditTags} from './edit-tags/edit-tags';
import RecipeEditDto from '../../DTO/entity_dto/recipe/recipe-edit.dto';
import {NotificationService} from '../../security/notification-service';

@Component({
  selector: 'app-edit-recipe',
  imports: [
    EditRecipeMainInfoSection,
    EditRecipeIngredientSection,
    EditRecipeCookingStages,
    EditTags,
    MatButton,
  ],
  templateUrl: './edit-recipe.html',
  styleUrl: './edit-recipe.css',
})
export class EditRecipeComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly recipeService = inject(RecipeService);
  private readonly imageService = inject(ImageModelService);
  private readonly imageUploadService = inject(ImageUploadService);
  private readonly notificationService = inject(NotificationService);
  private readonly route = inject(Router);

  recipeId = Number(this.activatedRoute.snapshot.paramMap.get(RECIPE_ID));
  recipe = signal<RecipeShowDTO>({} as RecipeShowDTO);
  recipeEdit = signal<RecipeEditProjection>({} as RecipeEditProjection);


  ngOnInit(): void {
    this.recipeService.showRecipe(this.recipeId).pipe(
      switchMap((data) =>
        this.imageService.getImage(data.illustrationID).pipe(
          switchMap((blob) =>
            from(this.imageUploadService.convertBlobToDataUrl(blob)),
          ),
          map((url) => ({...data, illustration: url})),
          catchError(() => of(data)),
        ),
      ),
      tap((recipe) => {
          this.recipe.set(recipe);
          console.log(recipe);
        }
      )

    ).subscribe();
  }

  protected saveRecipe() {
    const edit = this.recipeEdit();

    if (edit.titleImage) {
      this.imageService.updateImage(edit.titleImage, this.recipe().illustrationID).subscribe();
    }
    if (edit.editedStages && edit.editedStages.length > 0) {
      edit.editedStages
        .filter((stage) => stage.image && stage.imageID != null)
        .forEach((stage) => this.imageService.updateImage(stage.image!, stage.imageID!).subscribe());
    }

    const toServer = {
      id: this.recipeId,
      name: edit.name,
      summary: edit.summary,
      cookingTime: edit.cookingTime,
      addTags: edit.addTags ?? [],
      removeTags: edit.removeTags ?? [],
      isPrivate: edit.isPrivate,
      editedStages: (edit.editedStages ?? [])
        .filter((stage) => stage?.id != null)
        .map((stage) => ({
          id: stage.id,
          description: stage.description,
        })),
      editedIngredients: edit.editedIngredients ?? [],
    } as RecipeEditDto;
    console.dir(JSON.stringify(toServer));
    this.recipeService.updateRecipe(toServer).subscribe({
      error: () => {
        this.notificationService.showSnackBar("Ошибка");
      },
      complete: () => {
        this.route.navigate(['/', RECIPE, this.recipeId.toString()])
          .then(() => globalThis.location.reload());
      }
    });
  }


  protected cancel() {
    this.route.navigate(['/', RECIPE, this.recipeId.toString()])
      .then(() => globalThis.location.reload());
  }
}
