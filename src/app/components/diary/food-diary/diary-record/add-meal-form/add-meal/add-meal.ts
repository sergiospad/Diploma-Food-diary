import {Component, inject, model, signal} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatIcon} from '@angular/material/icon';
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from '@angular/material/autocomplete';
import {MatButton} from '@angular/material/button';
import {AsyncPipe} from '@angular/common';
import NutritionShowProjection from '../../../../../../DTO/entity_dto/nutritional_info/nutrition-show.projection';
import {combineLatest, Observable, of} from 'rxjs';
import ProductSearchDTO from '../../../../../../DTO/entity_dto/product/product-search.dto';
import RecipeTitleSearchDTO from '../../../../../../DTO/entity_dto/recipe/recipe-title-search.dto';
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
import RecipeSummarySearchDTO from '../../../../../../DTO/entity_dto/recipe/recipe-summary-search.dto';
import ProductService from '../../../../../../service/product.service';
import {markWord} from '../../../../../../util/regex-search-editor';
import MealItemCreateView from '../../../../../../DTO/entity_dto/diary/meal_item/meal-item-create.view';
import RecipeService from '../../../../../../service/recipe.service';

@Component({
  selector: 'app-add-meal',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatIcon,
    MatInput,
    MatAutocompleteTrigger,
    MatAutocomplete,
    MatOption,
    MatButton,
    AsyncPipe,
    MatLabel
  ],
  templateUrl: './add-meal.html',
  styleUrl: './add-meal.css',
})
export class AddMeal {

  private readonly productService = inject(ProductService);
  private readonly fb = inject(FormBuilder);
  private readonly recipeService = inject(RecipeService);

  mealItemForm: FormGroup = this.createMealItemForm();
  mealItems = model.required<MealItemCreateView[]>();
  createdMealItem = signal<MealItemCreateView>({}as MealItemCreateView);

  nutritionInfo = signal<NutritionShowProjection|undefined>(undefined);

  private searchPending = 0;
  protected isSearchLoading = false;

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

  private createMealItemForm() {
    return this.fb.group({
      searchControl: new FormControl("", { nonNullable: true, validators: Validators.required}),
      weightControl: new FormControl(null, [Validators.required, Validators.min(0.1), Validators.max(5000)])
    });
  }

  protected readonly markWord = markWord;


  protected onWeightChange() {
    if(this.nutritionInfo()){
      this.createdMealItem.set(this.createView());
    }
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

  private createView():MealItemCreateView{
    const coeff = this.mealItemForm.controls['weightControl'].value/100;
    return {
      nutritionID: this.nutritionInfo()?.ID,
      nutritionName: this.nutritionInfo()?.name,
      nutritionType: this.nutritionInfo()?.type,
      weight: this.mealItemForm.controls['weightControl'].value,
      calories: this.nutritionInfo()!.calories*coeff,
      proteins: this.nutritionInfo()!.proteins*coeff,
      fat: this.nutritionInfo()!.fat*coeff,
      carbs: this.nutritionInfo()!.carbs*coeff,
    }as MealItemCreateView;
  }

  containsAlreadyNutr(id:number) {
    return this.mealItems().some(item=>item.nutritionID===id);
  }
}
