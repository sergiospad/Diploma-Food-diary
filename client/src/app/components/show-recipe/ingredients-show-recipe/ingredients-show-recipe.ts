import {Component, effect, inject, input} from '@angular/core';
import IngredientShowDTO from '../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-show.dto';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {FormsModule} from '@angular/forms';
import IngredientService from '../../../service/recipe_resource/ingredient.service';
import IngredientChangeDTO from '../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-change.dto';
import {DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-ingredients-show-recipe',
  imports: [
    MatOption,
    MatSelect,
    FormsModule,
    DecimalPipe,
  ],
  templateUrl: './ingredients-show-recipe.html',
  styleUrl: './ingredients-show-recipe.css',
})
export class IngredientsShowRecipe {
  ingredientService = inject(IngredientService);

  ingredients = input<IngredientShowDTO[]>([]);

  private readonly selectedUnitIdByIngredientId = new Map<number, number>();


  constructor() {
    effect(() => {
      for (const ing of this.ingredients()) {
        if (!this.selectedUnitIdByIngredientId.has(ing.id) && ing.units?.length) {
          this.selectedUnitIdByIngredientId.set(ing.id, ing.units[0].id);

        }
      }
    });
  }

  protected selectedUnitId(ing: IngredientShowDTO): number {
    const stored = this.selectedUnitIdByIngredientId.get(ing.id);
    if (stored != null && ing.units.some((u) => u.id === stored)) {
      return stored;
    }
    return ing.units[0].id ?? 0;
  }

  protected onUnitChange(ing: IngredientShowDTO, unitId: number): void {
    this.selectedUnitIdByIngredientId.set(ing.id, unitId);
    const request = {
      ingredientID:ing.id,
      measureID:unitId,
    }as IngredientChangeDTO
    this.ingredientService.toggleMeasureUnit(request)
      .subscribe(data => ing.amount = data.amount);

  }
}
