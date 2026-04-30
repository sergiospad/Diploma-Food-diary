import {inject, Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import ProductSearchDTO from '../DTO/entity_dto/product/product-search.dto';
import {FormControl} from '@angular/forms';
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
import ProductService from '../service/product.service';


export default class ProductSearchEngineService{
  private readonly productService = inject(ProductService);

  private isProductSearchLoading = false;
  private productSearchPending = 0;

  public getSearchResults(productSearchControl:FormControl<string>):Observable<ProductSearchDTO[]>{
    return productSearchControl.valueChanges.pipe(
      startWith(productSearchControl.value),
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
  }

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

  getSearchLoading():boolean{
    return this.isProductSearchLoading;
  }
}
