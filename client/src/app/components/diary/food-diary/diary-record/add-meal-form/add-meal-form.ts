import { Component, computed, DestroyRef, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AddMealDialogData, mealMap } from '../diary-record';
import MealShowDTO from '../../../../../DTO/entity_dto/diary/meal/meal-show.dto';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import MealCreateDTO from '../../../../../DTO/entity_dto/diary/meal/meal-create.dto';
import { MatFormField, MatInput, MatLabel} from '@angular/material/input';
import { MealType } from '../../../../../DTO/types';
import { MatButton } from '@angular/material/button';
import MealItemCreateView from '../../../../../DTO/entity_dto/diary/meal_item/meal-item-create.view';
import MealItemCreateDTO from '../../../../../DTO/entity_dto/diary/meal_item/meal-item-create.dto';
import MealService from '../../../../../service/diary/meal.service';
import {AddMeal} from './add-meal/add-meal';
import BaseNutrition from '../../../../../DTO/entity_dto/nutritional_info/base-nurtition';
import {ShowMealItems} from './show-meal-items/show-meal-items';

@Component({
  selector: 'app-meal-form',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatButton,
    AddMeal,
    ShowMealItems,
  ],
  templateUrl: './add-meal-form.html',
  styleUrl: './add-meal-form.css',
})
export class AddMealForm {
  private readonly dialogData = inject<AddMealDialogData>(MAT_DIALOG_DATA);
  private readonly mealService = inject(MealService);

  private readonly dialogRef = inject(MatDialogRef<AddMealForm, MealShowDTO[] | undefined>);
  private readonly fb = inject(FormBuilder);
  private readonly destroyRef = inject(DestroyRef);

  mealForm: FormGroup = this.createMealForm();

  mealItems = signal<MealItemCreateView[]>([]);
  protected readonly mealItemsTotals = computed(() => {
    const totals = {
      calories: 0,
      proteins: 0,
      fat: 0,
      carbs: 0,
    } as BaseNutrition;

    for (const item of this.mealItems()) {
      totals.calories += Number(item.calories ?? 0);
      totals.proteins += Number(item.proteins ?? 0);
      totals.fat += Number(item.fat ?? 0);
      totals.carbs += Number(item.carbs ?? 0);
    }

    return totals;
  });
  selectedMeal = signal<MealShowDTO | null>(null);

  meals = signal<MealShowDTO[]>(this.dialogData.meals);
  date = this.dialogData.dailyRecordDate;

  constructor() {
    this.bindSelectedMealToMealType();
  }

  createMealForm() {
    return this.fb.group({
      mealType: new FormControl<MealType>('BREAKFAST', [Validators.required]),
      mealTime: new FormControl(null, [Validators.required]),
    });
  }

  protected readonly mealMap = mealMap;


  protected submitMeal() {
    const itemsToServer = this.mealItems()
      .map(view=> view as MealItemCreateDTO);
    const mealCreateDto = {
      dailyRecordDate: this.date,
      mealType: this.mealForm.controls['mealType'].value,
      time: this.mealForm.controls['mealTime'].value,
      list: itemsToServer
    } as MealCreateDTO;
    this.mealService.createMeal(mealCreateDto).subscribe((meal) => {
      this.meals.update((list) => [...list, meal]);
      this.selectedMeal.set(meal);
      this.mealItems.set([]);
    });
  }

  protected close(){
    this.dialogRef.close(this.meals());
  }

  canSubmit(){
      return this.mealItems().length!=0;
  }

  private bindSelectedMealToMealType(): void {
    const mealTypeControl = this.mealForm.controls['mealType'] as FormControl<MealType>;

    const updateSelectedMeal = (mealType: MealType) => {
      const meal = this.meals().find((m) => m.mealType === mealType);
      this.selectedMeal.set(meal ?? null);
    };

    updateSelectedMeal(mealTypeControl.value);

    mealTypeControl.valueChanges
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((mealType) => {
        updateSelectedMeal(mealType);
      });
  }
}
