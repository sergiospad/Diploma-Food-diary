import {Component, inject, model, OnInit, signal} from '@angular/core';
import {AddIngredient} from "../../add-recipe/recipe-main-info-ingredients-section/add-ingredient/add-ingredient";
import {
    IngredientsShowSection
} from "../../add-recipe/recipe-main-info-ingredients-section/ingredients-show-section/ingredients-show-section";
import {MatButton} from "@angular/material/button";
import RecipeShowDTO from '../../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditProjection from '../../../DTO/entity_dto/recipe/recipe-edit-projection';
import {CookingTimeStepper} from '../../add-recipe/recipe-main-info-ingredients-section/cooking-time-stepper';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import AddProductComponent from '../../add-recipe/recipe-main-info-ingredients-section/add-product/add-product';
import {EditRecipeIngredientsShow} from './edit-recipe-ingredients-show/edit-recipe-ingredients-show';

@Component({
  selector: 'app-edit-recipe-ingredient-section',
  imports: [
    AddIngredient,
    IngredientsShowSection,
    MatButton,
    EditRecipeIngredientsShow
  ],
  templateUrl: './edit-recipe-ingredient-section.html',
  styleUrl: './edit-recipe-ingredient-section.css',
})
export class EditRecipeIngredientSection implements OnInit {
  private readonly dialog = inject(MatDialog);
  recipe = model<RecipeShowDTO>({}as RecipeShowDTO);
  recipeEdit = model<RecipeEditProjection>({} as RecipeEditProjection);
  isPrivate = signal(false);
  needsCookingTime = signal(false);

  private readonly stepper = new CookingTimeStepper(5, 5);

  protected changeIsPrivate(checked: boolean) {
      this.isPrivate.set(checked);
      this.recipeEdit().isPrivate = checked;
  }

  ngOnInit(): void {
    if (this.recipe().cookingTime)
      this.needsCookingTime = signal(true);
    else this.needsCookingTime = signal(false);
  }

  protected onNeedsCookingTime(checked: boolean) {
    this.needsCookingTime.set(checked);
    if(!checked)
      this.recipeEdit().cookingTime = undefined;
  }

  protected onCookingTimeInput(event: Event): void {
    const el = event.target as HTMLInputElement;
    const parsed = this.stepper.parseInput(el.value);
    if(parsed!=null) {
      this.recipe().cookingTime = parsed;
      this.recipeEdit().cookingTime = parsed;
    }
  }

  protected onCookingTimeBlur(event: Event): void {
    const el = event.target as HTMLInputElement;
    const parsed = this.stepper.parseInput(el.value)??30;
    this.recipe().cookingTime = parsed!;
    this.recipeEdit().cookingTime = parsed??undefined;
  }

  protected openAddProductDialog() {
    const dialogAddProductConfig = new MatDialogConfig();
    dialogAddProductConfig.width = "600px";
    this.dialog.open(AddProductComponent, dialogAddProductConfig);
  }

}
