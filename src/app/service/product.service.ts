import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../util/endpoint';
import ProductCreateDTO from '../DTO/product/product-create.dto';
import {Observable} from 'rxjs';
import ProductEditDTO from '../DTO/product/product-edit.dto';
import ProductSearchDTO from '../DTO/product/product-search.dto';
import MeasureUnitDTO from '../DTO/recipe-recource/measure-unit.dto';
import NutritionShowProjection from '../DTO/nutritional_info/nutrition-show.projection';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly productAPI = new Endpoint('product');

  createProduct(product: ProductCreateDTO):Observable<number>{
    return this.http.put<number>(
      this.productAPI.builder()
        .points("create")
        .build(),
      product)
  }

  updateProduct(product: ProductEditDTO):Observable<any>{
    return this.http.patch(
      this.productAPI.builder()
        .points("update")
        .build(),
      product)
  }

  productSearch(searchItem:string):Observable<ProductSearchDTO[]>{
    return this.http.get<ProductSearchDTO[]>(
      this.productAPI.builder()
        .points("search")
        .addParam("searchItem", searchItem)
        .build()
    )
  }

  getAllMeasureUnitsAll(productId: number): Observable<MeasureUnitDTO[]> {
    return this.http.get<MeasureUnitDTO[]>(
      this.productAPI.builder()
        .points("measureUnits")
        .addParam("productId", productId.toString())
        .build(),
    );
  }

  getNutrition(id: number):Observable<NutritionShowProjection>{
    return this.http.get<NutritionShowProjection>(
      this.productAPI.builder()
        .points("nutrition")
        .addParam("id", id.toString())
        .build());
  }
}
