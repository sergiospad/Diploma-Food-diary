import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import ProductSearchDTO from '../../../DTO/entity_dto/product/product-search.dto';
import MeasureUnitDTO from '../../../DTO/entity_dto/recipe-recource/measure-unit.dto';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, finalize, map, shareReplay, startWith, switchMap} from 'rxjs/operators';
import ProductService from '../../../service/product.service';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from '@angular/material/autocomplete';
import {AsyncPipe} from '@angular/common';
import {NgSelectComponent} from '@ng-select/ng-select';

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
    NgSelectComponent,
  ],
  templateUrl: './add-ingredient.html',
  styleUrl: './add-ingredient.css',
})
export class AddIngredient implements OnInit {
  public ingredientForm!: FormGroup;
  private readonly formBuilder = inject(FormBuilder);
  private readonly productService = inject(ProductService);
  protected readonly productSearchControl = new FormControl('', { nonNullable: true });
  protected isProductSearchLoading = false;
  private productSearchPending = 0;

  protected chosenProduct?: ProductSearchDTO;
  protected chosenMeasureUnit?:MeasureUnitDTO;
  protected searchMeasureUnitResults?:MeasureUnitDTO[]

  protected displayProductName = (product: ProductSearchDTO | string | null) => {
    if (!product) return null;
    return typeof product === 'string' ? product : product.name;
  };

  ngOnInit(): void {
   this.ingredientForm = this.createIngredientForm();
  }

  createIngredientForm(){
    return this.formBuilder.group({
      productID:[null, Validators.compose([Validators.required])],
      amount:[null, Validators.compose([Validators.required,  Validators.min(0.01),])],
      measureUnitID:[null, Validators.compose([Validators.required])],
    });
  }

  protected readonly searchProductResults$: Observable<ProductSearchDTO[]> =
    this.productSearchControl.valueChanges.pipe(
      startWith(this.productSearchControl.value),
      map((value) => value.trim()),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((query) =>
        query.length < 2
          ? of([])
          : this.withProductSearchLoading(this.productService.productSearch(query)).pipe(
            catchError(() => of([])),
          ),
      ),
      shareReplay({bufferSize: 1, refCount: true}),
    );

  private withProductSearchLoading<T>(source: Observable<T>): Observable<T> {
    this.productSearchPending += 1;
    this.isProductSearchLoading = true;
    return source.pipe(
      finalize(() => {
        this.productSearchPending -= 1;
        this.isProductSearchLoading = this.productSearchPending > 0;
      }),
    );
  }

  protected onMeasureUnitSearchSelect(product: ProductSearchDTO): void {
    this.chosenProduct = product;
    this.ingredientForm.patchValue({
      productID: product.id,
      measureUnitID: null,
    });
    this.productSearchControl.setValue(product.name, { emitEvent: false });
    this.productService
      .getAllMeasureUnits(product.id)
      .subscribe((data) => (this.searchMeasureUnitResults = data));
  }

  protected onProductQueryInput(): void {
    this.chosenProduct = undefined;
    this.searchMeasureUnitResults = [];
    this.ingredientForm.patchValue({
      productID: null,
      measureUnitID: null,
    });
  }
}
