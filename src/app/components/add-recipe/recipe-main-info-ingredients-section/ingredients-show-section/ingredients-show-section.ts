import {Component, input, model} from '@angular/core';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import IngredientCreateView from '../../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-create.view';

@Component({
  selector: 'app-ingredients-show-section',
  imports: [
    MatIconButton,
    MatIcon
  ],
  templateUrl: './ingredients-show-section.html',
  styleUrl: './ingredients-show-section.css',
})
export class IngredientsShowSection {
  ingredients = model<IngredientCreateView[]>([]);

  canRemove = input<boolean>(false);

  removeIngredient(index: number): void {
    this.ingredients.update((list) => list.filter((_, i) => i !== index));
  }

}
