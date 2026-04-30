import {Component, inject, model, OnInit, output, signal} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import { MatOption} from "@angular/material/autocomplete";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatInput, MatLabel} from "@angular/material/input";
import {MatSelect} from "@angular/material/select";
import IngredientShowDTO from '../../../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-show.dto';
import ProductSearchDTO from '../../../../../DTO/entity_dto/product/product-search.dto';
import IngredientEditDTO from '../../../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-edit.dto';

@Component({
  selector: 'app-edit-ingredient',
    imports: [
        FormsModule,
        MatButton,
        MatFormField,
        MatInput,
        MatLabel,
        MatOption,
        MatSelect,
        ReactiveFormsModule
    ],
  templateUrl: './edit-ingredient.html',
  styleUrl: './edit-ingredient.css',
})
export class EditIngredient implements OnInit {

  ingredient = model<IngredientShowDTO>({}as IngredientShowDTO);
  copyIngredient?:IngredientShowDTO;
  toMainComponentIngredientChange = output<IngredientEditDTO|null>();
  ingredientEditDTO = signal<IngredientEditDTO>({}as IngredientEditDTO);
  public ingredientForm!: FormGroup;
  private readonly formBuilder = inject(FormBuilder);
  chosenProduct = signal<ProductSearchDTO|undefined>(undefined );

  ngOnInit(): void {
    this.ingredientForm = this.createIngredientForm();
    this.copyIngredient = this.copy();
    this.ingredientEditDTO().id = this.ingredient().id ;
  }

  createIngredientForm() {

    this.chosenProduct.set(undefined);
    const group = this.formBuilder.group({
      amount: [this.ingredient().amount, Validators.compose([Validators.required, Validators.min(0.01)])],
      measureUnitID: [this.ingredient().units[0].id, Validators.compose([Validators.required])],
    });
    return group;
  }

  protected submitChanges() {
    this.toMainComponentIngredientChange.emit(this.ingredientEditDTO());
    this.resetForm();
  }

  copy():IngredientShowDTO{
    return {
      id: this.ingredient().id,
      productName: this.ingredient().productName,
      amount: this.ingredient().amount,
      units: this.ingredient().units,
    }as IngredientShowDTO;
  }

  resetForm():void{
    this.ingredientForm.patchValue({
      measureUnitID: null,
      amount: null,
    });
  }

  onAbort(){
    this.ingredient.set(this.copyIngredient!);
    this.toMainComponentIngredientChange.emit(null);
    this.resetForm();
  }


  protected onChangeAmount(value: string) {
    const num = Number.parseFloat(value);
    this.ingredient().amount = num;
    this.ingredientEditDTO().amount = num;
  }

  protected onChangeMeasureUnit(value: number) {
    const unit = this.ingredient().units.find(unit => unit.id === value)!;
    const ind = this.ingredient().units.findIndex(unit => unit.id === value);
    this.ingredient().units.splice(ind, 1);
    this.ingredient().units.unshift(unit);
    this.ingredientEditDTO().measureUnitID = value;
  }
}
