import {Component, inject, model, output, signal} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {AddIngredient} from './add-ingredient/add-ingredient';
import IngredientCreateView from '../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-create.view';
import {CookingTimeStepper} from './cooking-time-stepper';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import AddProductComponent from './add-product/add-product';
import {IngredientsShowSection} from './ingredients-show-section/ingredients-show-section';

@Component({
  selector: 'app-recipe-main-info-ingredients-section',
  imports: [
    MatButton,
    AddIngredient,
    IngredientsShowSection
  ],
  templateUrl: './recipe-main-info-ingredients-section.html',
  styleUrl: './recipe-main-info-ingredients-section.css',
})
export class RecipeMainInfoIngredientsSection {

  private readonly dialog = inject(MatDialog);

  protected isPrivate = signal(false);
  protected toMainComponentIsPrivate = output<boolean>();

  protected cookingTime = signal(30);
  protected toMainComponentCookingTime = output<number|undefined>();

  ingredients=model<IngredientCreateView[]>([]);
  protected toMainComponentIngredients = output<IngredientCreateView[]>();

  protected needsTime = signal(false);

  private readonly stepper = new CookingTimeStepper(5, 5);

  protected onCookingTimeInput(event: Event): void {
    //TODO продумать добавление директивы
    const el = event.target as HTMLInputElement;
    const parsed = this.stepper.parseInput(el.value);
    if(parsed!=null) {
      this.cookingTime.set(parsed)
      this.toMainComponentCookingTime.emit(parsed)
    }
  }

  protected onCookingTimeBlur(event: Event): void {
    const el = event.target as HTMLInputElement;
    const parsed = this.stepper.parseInput(el.value);
    this.cookingTime.set(parsed!);
    this.toMainComponentCookingTime.emit(parsed??undefined)
  }

  protected addIngredientToList($event: IngredientCreateView) {
    let newIng = this.ingredients()??[];
    newIng.push($event);
    this.ingredients.set(newIng);
    this.toMainComponentIngredients.emit(newIng);
  }

  protected removeIngredient(index: number): void {
    let newIng = this.ingredients() ?? [];
    newIng.splice(index, 1);
    this.ingredients.set(newIng);
    this.toMainComponentIngredients.emit(newIng);
  }

  protected openAddProductDialog() {
    const dialogAddProductConfig = new MatDialogConfig();
    dialogAddProductConfig.width = "600px";
    this.dialog.open(AddProductComponent, dialogAddProductConfig);
  }

}
