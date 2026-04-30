import {Component, model, signal} from '@angular/core';
import RecipeShowDTO from '../../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditProjection from '../../../DTO/entity_dto/recipe/recipe-edit-projection';
import {
  CookingStageShowSection
} from '../../add-recipe/cooking-stage-section/cooking-stage-show-section/cooking-stage-show-section';
import CookingStageShowDTO from '../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-show.dto';
import CookingStageEditProjection
  from '../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-edit-projection';
import CookingStageCreateView from '../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.view';
import {EditRecipeShowStage} from './edit-recipe-show-stage/edit-recipe-show-stage';
import {EditCookingStage} from './edit-cooking-stage/edit-cooking-stage';

@Component({
  selector: 'app-edit-recipe-cooking-stages',
  imports: [
    CookingStageShowSection,
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
