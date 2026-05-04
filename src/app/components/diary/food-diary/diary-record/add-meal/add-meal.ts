import {Component, inject, OnInit, signal} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {
  AddCoefficientDialogData
} from '../../../../categories/show-category/show-coefficient/add-coefficient/add-coefficient';
import {AddMealDialogData, mealMap} from '../diary-record';
import CategoryShowDto from '../../../../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import MealShowDTO from '../../../../../DTO/entity_dto/diary/meal/meal-show.dto';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import MealCreateDTO from '../../../../../DTO/entity_dto/diary/meal/meal-create.dto';
import NutritionShowProjection from '../../../../../DTO/entity_dto/nutritional_info/nutrition-show.projection';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MealType} from '../../../../../DTO/types';

@Component({
  selector: 'app-add-meal',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatLabel
  ],
  templateUrl: './add-meal.html',
  styleUrl: './add-meal.css',
})
export class AddMeal implements OnInit {
  private readonly dialogData = inject<AddMealDialogData>(MAT_DIALOG_DATA);
  private readonly dialogRef = inject(MatDialogRef<AddMeal, MealShowDTO | undefined>);
  private readonly fb = inject(FormBuilder);

  mealForm!: FormGroup;
  mealItemForm!: FormGroup;

  mealItems= signal<MealCreateDTO[]>([]);
  selectedMealType = signal<MealType>("BREAKFAST");
  selectedMeal = signal<MealShowDTO|null>(null);
  nutritionInfo = signal<NutritionShowProjection>({} as NutritionShowProjection);
  weight = signal<number>(0);

  meals = this.dialogData.meals;

  ngOnInit(): void {
    this.mealForm = this.createMealForm();
    this.mealItemForm = this.createMealItemForm();
  }

  createMealItemForm(){
    return this.fb.group({
      mealItemNutrID: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(5000)]),
      mealWeight: new FormControl(null, [Validators.required]),
    })
  }

  createMealForm(){
    return this.fb.group({
      mealType: new FormControl(null, [Validators.required]),
      mealTime: new FormControl(null, [Validators.required])
    })
  }

  protected readonly mealMap = mealMap;


  protected onSelectedMealType(val: MealType) {
    this.selectedMealType.set(val);
    const meal = this.meals.find(m=> m.mealType === val);
    this.selectedMeal.set(meal??null);
  }
}
