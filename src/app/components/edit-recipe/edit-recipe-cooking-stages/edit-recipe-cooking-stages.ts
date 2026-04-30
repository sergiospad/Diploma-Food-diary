import {Component, model, signal} from '@angular/core';
import RecipeShowDTO from '../../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditProjection from '../../../DTO/entity_dto/recipe/recipe-edit-projection';
import {EditRecipeShowStage} from './edit-recipe-show-stage/edit-recipe-show-stage';
import {EditCookingStage} from './edit-cooking-stage/edit-cooking-stage';

@Component({
  selector: 'app-edit-recipe-cooking-stages',
  imports: [
    EditRecipeShowStage,
    EditCookingStage
  ],
  templateUrl: './edit-recipe-cooking-stages.html',
  styleUrl: './edit-recipe-cooking-stages.css',
})
export class EditRecipeCookingStages {
  recipe = model<RecipeShowDTO>({}as RecipeShowDTO);
  recipeEdit = model<RecipeEditProjection>({} as RecipeEditProjection);
  indexToEdit = signal<number>(-1);

  protected setEditIndex($event: number) {
    this.indexToEdit.set($event);
  }
}
