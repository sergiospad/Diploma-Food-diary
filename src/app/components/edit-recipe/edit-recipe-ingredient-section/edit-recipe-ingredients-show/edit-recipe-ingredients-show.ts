import {Component, model, signal} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import RecipeShowDTO from '../../../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditProjection from '../../../../DTO/entity_dto/recipe/recipe-edit-projection';
import {EditIngredient} from './edit-ingredient/edit-ingredient';
import IngredientShowDTO from '../../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-show.dto';
import IngredientEditDTO from '../../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-edit.dto';

@Component({
  selector: 'app-edit-recipe-ingredients-show',
  imports: [
    MatIcon,
    MatIconButton,
    EditIngredient,
  ],
  templateUrl: './edit-recipe-ingredients-show.html',
  styleUrl: './edit-recipe-ingredients-show.css',
})
export class EditRecipeIngredientsShow {
  recipe = model<RecipeShowDTO>({} as RecipeShowDTO);
  recipeEdit = model<RecipeEditProjection>({} as RecipeEditProjection);
  indexToEdit = signal(-1);

  protected removeIngredient($index: number) {
    const id = this.recipe().ingredients[$index].id;
    this.recipe().ingredients.splice($index, 1);
    this.recipeEdit().removedIngredients.push(id);
  }


  protected editIngredient($index: number) {
    this.indexToEdit.set($index);
  }

  protected onIngredientChanged(event: IngredientEditDTO | null): void {
    if (event === null) {
      this.indexToEdit.set(-1);
      return;
    }
    this.recipeEdit().editedIngredients.push(event);
    this.indexToEdit.set(-1);
  }
}
