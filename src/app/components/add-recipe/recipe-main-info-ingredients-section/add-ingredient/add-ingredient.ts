import {Component, inject, OnInit, output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import ProductSearchDTO from '../../../../DTO/entity_dto/product/product-search.dto';
import MeasureUnitDTO from '../../../../DTO/entity_dto/recipe-recource/measure-unit.dto';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import ProductService from '../../../../service/product.service';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from '@angular/material/autocomplete';
import {AsyncPipe} from '@angular/common';
import {MatButton} from '@angular/material/button';
import IngredientCreateView from '../../../../DTO/entity_dto/recipe-recource/ingredient/ingredient-create.view';
import {MatSelect} from '@angular/material/select';
import ProductSearchEngineService from '../../../../util/product-search-engine.service';

@Component({
  selector: 'app-add-ingredient',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatAutocompleteTrigger,
    MatAutocomplete,
    MatOption,
    AsyncPipe,
    MatError,
    MatButton,
    MatSelect,
  ],
  templateUrl: './add-ingredient.html',
  styleUrl: './add-ingredient.css',
})
export class AddIngredient implements OnInit {
  public ingredientForm!: FormGroup;
  private readonly formBuilder = inject(FormBuilder);
  private readonly productService = inject(ProductService);
  protected readonly productSearchControl = new FormControl('', { nonNullable: true });
  protected productSearchService = new ProductSearchEngineService();

  protected chosenProduct?: ProductSearchDTO;
  protected searchMeasureUnitResults?:MeasureUnitDTO[]
  formedIngredient = output<IngredientCreateView>();

  protected displayProductName = (value: any): string => {
    if (!value) return '';
    return typeof value === 'string' ? value : (value.name ?? '');
  };

  ngOnInit(): void {
    this.ingredientForm = this.createIngredientForm();
  }

  createIngredientForm() {
    this.chosenProduct = undefined;
    const group = this.formBuilder.group({
      productID: [null, Validators.compose([Validators.required])],
      amount: [null, Validators.compose([Validators.required, Validators.min(0.01)])],
      measureUnitID: [null, Validators.compose([Validators.required])],
    });
    group.get('measureUnitID')?.disable({ emitEvent: false });
    return group;
  }

  protected readonly searchProductResults$: Observable<ProductSearchDTO[]> =
    this.productSearchService.getSearchResults(this.productSearchControl);

  protected getSearchLoading():boolean{
    return this.productSearchService.getSearchLoading();
  }


  protected onMeasureUnitSearchSelect(product: ProductSearchDTO): void {
    this.chosenProduct = product;
    this.ingredientForm.patchValue({
      productID: product.id,
      measureUnitID: null,
    });
    this.productSearchControl.setValue(product.name, { emitEvent: false });
    this.ingredientForm.get('measureUnitID')?.disable({ emitEvent: false });
    this.searchMeasureUnitResults = [];
    this.productService
      .getAllMeasureUnits(product.id)
      .pipe(catchError(() => of([] as MeasureUnitDTO[])))
      .subscribe((data) => {
        this.searchMeasureUnitResults = data;
        this.ingredientForm.get('measureUnitID')?.enable({ emitEvent: false });
      });
  }

  protected onProductQueryInput(): void {
    this.chosenProduct = undefined;
    this.searchMeasureUnitResults = [];
    this.ingredientForm.patchValue({
      productID: null,
      measureUnitID: null,
    });
    this.ingredientForm.get('measureUnitID')?.disable({ emitEvent: false });
  }

  protected addToNewProductCard() {
    this.ingredientForm.markAllAsTouched();
    const raw = this.ingredientForm.getRawValue();
    if (!this.chosenProduct || raw.productID == null || raw.amount == null || raw.measureUnitID == null) {
      return;
    }

    const selectedMeasureUnitId = raw.measureUnitID as number;
    const selectedMeasureUnit = this.searchMeasureUnitResults?.find(
      (unit) => unit.id === selectedMeasureUnitId,
    );
    if (!selectedMeasureUnit) {
      this.ingredientForm.get('measureUnitID')?.setErrors({ required: true });
      return;
    }

    const ingredient = {
      productID: this.chosenProduct.id,
      measureUnitID: selectedMeasureUnit.id,
      productName: this.chosenProduct.name,
      measureUnitName: selectedMeasureUnit.name,
      amount: Number(this.ingredientForm.value.amount),
    } as IngredientCreateView;
    this.formedIngredient.emit(ingredient);
    this.ingredientForm = this.createIngredientForm();
    this.productSearchControl.setValue('', { emitEvent: false });
    this.searchMeasureUnitResults = [];
  }
}
