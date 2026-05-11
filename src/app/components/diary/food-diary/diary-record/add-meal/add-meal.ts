import { Component, computed, DestroyRef, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AddMealDialogData, mealMap } from '../diary-record';
import CategoryShowDto from '../../../../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import MealShowDTO from '../../../../../DTO/entity_dto/diary/meal/meal-show.dto';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import MealCreateDTO from '../../../../../DTO/entity_dto/diary/meal/meal-create.dto';
import NutritionShowProjection from '../../../../../DTO/entity_dto/nutritional_info/nutrition-show.projection';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import { MealType } from '../../../../../DTO/types';
import {combineLatest, Observable, of} from 'rxjs';
import RecipeTitleSearchDTO from '../../../../../DTO/entity_dto/recipe/recipe-title-search.dto';
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  finalize,
  map,
  shareReplay,
  startWith,
  switchMap
} from 'rxjs/operators';
import RecipeService from '../../../../../service/recipe.service';
import ProductService from '../../../../../service/product.service';
import {AsyncPipe} from '@angular/common';
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from '@angular/material/autocomplete';
import {MatIcon} from '@angular/material/icon';
import { MatButton } from '@angular/material/button';
import {markWord} from '../../../../../util/regex-search-editor';
import ProductSearchDTO from '../../../../../DTO/entity_dto/product/product-search.dto';
import RecipeSummarySearchDTO from '../../../../../DTO/entity_dto/recipe/recipe-summary-search.dto';
import {RECIPE} from '../../../../../util/roots';
import MealItemCreateView from '../../../../../DTO/entity_dto/diary/meal_item/meal-item-create.view';
import MealItemCreateDTO from '../../../../../DTO/entity_dto/diary/meal_item/meal-item-create.dto';
import MealService from '../../../../../service/diary/meal.service';

@Component({
  selector: 'app-add-meal',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    AsyncPipe,
    MatAutocomplete,
    MatAutocompleteTrigger,
    MatIcon,
    MatButton,
    MatOption,
    MatError
  ],
  templateUrl: './add-meal.html',
  styleUrl: './add-meal.css',
})
export class AddMeal {
  private readonly dialogData = inject<AddMealDialogData>(MAT_DIALOG_DATA);
  private readonly recipeService = inject(RecipeService);
  private readonly productService = inject(ProductService);
  private readonly mealService = inject(MealService);

  private readonly dialogRef = inject(MatDialogRef<AddMeal, MealShowDTO | undefined>);
  private readonly fb = inject(FormBuilder);
  private readonly destroyRef = inject(DestroyRef);

  mealForm: FormGroup = this.createMealForm();
  mealItemForm: FormGroup = this.createMealItemForm();

  mealItems = signal<MealItemCreateView[]>([]);
  createdMealItem = signal<MealItemCreateView>({}as MealItemCreateView);
  protected readonly mealItemsTotals = computed(() => {
    const totals = {
      calories: 0,
      protein: 0,
      fat: 0,
      carbs: 0,
    };

    for (const item of this.mealItems()) {
      totals.calories += Number(item.calories ?? 0);
      totals.protein += Number(item.protein ?? 0);
      totals.fat += Number(item.fat ?? 0);
      totals.carbs += Number(item.carbs ?? 0);
    }

    return totals;
  });
  selectedMeal = signal<MealShowDTO | null>(null);
  nutritionInfo = signal<NutritionShowProjection|undefined>(undefined);
  private searchPending = 0;
  protected isSearchLoading = false;

  meals = signal<MealShowDTO[]>(this.dialogData.meals);
  date = this.dialogData.dailyRecordDate;

  constructor() {
    this.bindSelectedMealToMealType();
  }

  protected readonly searchResults$: Observable<Array<ProductSearchDTO | RecipeTitleSearchDTO>> =
    this.mealItemForm.controls["searchControl"]?.valueChanges.pipe(
      startWith( this.mealItemForm.controls["searchControl"]?.value),
      map((value) => (typeof value === 'string' ? value.trim() : '')),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((query) =>
        query.length < 2
          ? of<Array<ProductSearchDTO | RecipeTitleSearchDTO>>([])
          : this.withSearchLoading(
              combineLatest({
                products: this.productService
                  .productSearch(query)
                  .pipe(catchError(() => of<ProductSearchDTO[]>([]))),
                recipes: this.recipeService
                  .titleSearch(query)
                  .pipe(catchError(() => of<RecipeTitleSearchDTO[]>([]))),
              }).pipe(
                map(({ products, recipes }) => [...products, ...recipes]),
              ),
          ),
      ),
      shareReplay({bufferSize: 1, refCount: true}),
    );

  protected onSearchSelect(recipe: RecipeTitleSearchDTO | RecipeSummarySearchDTO): void {
    this.productService.getNutrition(recipe.id)
      .subscribe(data=> {

        this.nutritionInfo.set(data)
        const weight = this.mealItemForm.controls['weightControl'].value;
        if(weight)
          this.createdMealItem.set(this.createView());
      })
  }
  protected displayProductName = (value: any): string => {
    if (!value) return '';
    return typeof value === 'string' ? value : (value.name ?? '');
  };

  private withSearchLoading<T>(source: Observable<T>): Observable<T> {
    this.searchPending += 1;
    this.isSearchLoading = true;
    return source.pipe(
      finalize(() => {
        this.searchPending -= 1;
        this.isSearchLoading = this.searchPending > 0;
      }),
    );
  }


  createMealForm() {
    return this.fb.group({
      mealType: new FormControl<MealType>('BREAKFAST', [Validators.required]),
      mealTime: new FormControl(null, [Validators.required]),
    });
  }

  protected readonly mealMap = mealMap;

  protected readonly markWord = markWord;

  private createMealItemForm() {
    return this.fb.group({
      searchControl: new FormControl("", { nonNullable: true, validators: Validators.required}),
      weightControl: new FormControl(null, [Validators.required, Validators.min(0.1), Validators.max(5000)])
    });
  }

  protected onWeightChange(num: number) {
    if(this.nutritionInfo()){
      this.createdMealItem.set(this.createView());
    }
  }

  private createView():MealItemCreateView{
    const coeff = this.mealItemForm.controls['weightControl'].value/100;
    return {
      nutritionID: this.nutritionInfo()?.ID,
      nutritionName: this.nutritionInfo()?.name,
      nutritionType: this.nutritionInfo()?.type,
      weight: this.mealItemForm.controls['weightControl'].value,
      calories: this.nutritionInfo()!.calories*coeff,
      protein: this.nutritionInfo()!.protein*coeff,
      fat: this.nutritionInfo()!.fat*coeff,
      carbs: this.nutritionInfo()!.carbs*coeff,
    }as MealItemCreateView;
  }

  containsAlreadyNutr(id:number) {
    return this.mealItems().some(item=>item.nutritionID===id);
  }

  protected submitMealItem() {
    if (this.mealItemForm.invalid || !this.nutritionInfo()) {
      this.mealItemForm.markAllAsTouched();
      return;
    }

    this.mealItems.update(list=>([...list, this.createdMealItem()]));
    this.mealItemForm.reset();
    this.nutritionInfo.set(undefined);
    this.createdMealItem.set({} as MealItemCreateView);
  }

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
