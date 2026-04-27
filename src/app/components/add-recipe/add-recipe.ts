import {Component, inject, input, OnInit, signal} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import RecipeCreateDTO from '../../DTO/entity_dto/recipe/recipe-create.dto';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {CookingTimeStepper} from './cooking-time-stepper';
import IngredientCreateView from '../../DTO/entity_dto/recipe-recource/ingredient/ingredient-create.view';
import {AddIngredient} from './add-ingredient/add-ingredient';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import AddProductComponent from './add-product/add-product';

@Component({
  selector: 'app-add-recipe',
  imports: [
    MatInput,
    MatFormField,
    MatLabel,
    MatButton,
    MatIcon,
    AddIngredient
  ],
  templateUrl: './add-recipe.html',
  styleUrl: './add-recipe.css',
})
export class AddRecipeComponent implements OnInit {

  private readonly dialog = inject(MatDialog);
  protected title = signal('')
  protected titleImage?:Blob;
  protected summary = signal('');
  protected isPrivate = signal(false);
  protected needsTime = signal(false);
  protected cookingTime = signal(30);
  protected ingredients?:IngredientCreateView[]

  private readonly stepper = new CookingTimeStepper(5,5);

  protected readonly input = input;

  ngOnInit(): void {
    this.ingredients = [];
  }

  selectImage(): void {}

  protected onCookingTimeInput(event: Event): void {
    //TODO продумать добавление директивы
    const el = event.target as HTMLInputElement;
    const parsed = this.stepper.parseInput(el.value);
    if(parsed!=null) this.cookingTime.set(parsed);
  }

  protected onCookingTimeBlur(event: Event): void {
    const el = event.target as HTMLInputElement;
    this.cookingTime.set(this.stepper.snapOnBlur(el.value));
  }


  protected addToList($event: IngredientCreateView) {
    this.ingredients?.push($event);
  }

  protected openAddProductDialog() {
    const dialogAddProductConfig = new MatDialogConfig();
    dialogAddProductConfig.width = "600px";
    this.dialog.open(AddProductComponent, dialogAddProductConfig);
  }
}
