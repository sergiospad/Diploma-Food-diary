import {Component, inject, OnInit, signal} from '@angular/core';
import {RECIPE_ID} from '../../util/roots';
import {ActivatedRoute} from '@angular/router';
import RecipeService from '../../service/recipe.service';
import RecipeShowDTO from '../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditDto from '../../DTO/entity_dto/recipe/recipe-edit.dto';
import {CookingStageSection} from '../add-recipe/cooking-stage-section/cooking-stage-section';
import {MatButton} from '@angular/material/button';
import {
  RecipeMainInfoIngredientsSection
} from '../add-recipe/recipe-main-info-ingredients-section/recipe-main-info-ingredients-section';
import {RecipeMainInfoSectionComponent} from '../add-recipe/recipe-main-info-section/recipe-main-info-section';
import {TagShowSection} from '../add-recipe/tag-show-section/tag-show-section';
import {EditRecipeMainInfoSection} from './edit-recipe-main-info-section/edit-recipe-main-info-section';
import RecipeEditProjection from '../../DTO/entity_dto/recipe/recipe-edit-projection';
import {map, switchMap, tap} from 'rxjs/operators';
import ImageModelService from '../../service/recipe_resource/image-model.service';
import {ImageUploadService} from '../../image_services/image-upload.service';
import {from} from 'rxjs';
import {EditRecipeIngredientSection} from './edit-recipe-ingredient-section/edit-recipe-ingredient-section';
import {CookingTimeStepper} from '../add-recipe/recipe-main-info-ingredients-section/cooking-time-stepper';
import {EditRecipeCookingStages} from './edit-recipe-cooking-stages/edit-recipe-cooking-stages';

@Component({
  selector: 'app-edit-recipe',
  imports: [
    EditRecipeMainInfoSection,
    EditRecipeIngredientSection,
    EditRecipeCookingStages
  ],
  templateUrl: './edit-recipe.html',
  styleUrl: './edit-recipe.css',
})
export class EditRecipeComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly recipeService = inject(RecipeService);
  private readonly imageService = inject(ImageModelService);
  private readonly imageUploadService = inject(ImageUploadService);
  recipeId = Number(this.activatedRoute.snapshot.paramMap.get(RECIPE_ID));
  recipe = signal<RecipeShowDTO>({} as RecipeShowDTO);
  recipeEdit = signal<RecipeEditProjection>({} as RecipeEditProjection);


  ngOnInit(): void {
    this.recipeService.showRecipe(this.recipeId).pipe(
      tap(data => this.recipe.set(data)),
      switchMap(data => this.imageService.getImage(data.illustrationID)),
      switchMap(data=> from(this.imageUploadService.convertBlobToDataUrl(data))),
      tap(url=> this.recipe().illustration = url))
    .subscribe()
  }
}
