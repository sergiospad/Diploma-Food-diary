import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../util/endpoint';
import {Observable} from 'rxjs';
import MeasureUnitDTO from '../DTO/entity_dto/recipe-recource/measure-unit.dto';
import ProductCreateDTO from '../DTO/entity_dto/product/product-create.dto';
import ProductEditDTO from '../DTO/entity_dto/product/product-edit.dto';
import ProductSearchDTO from '../DTO/entity_dto/product/product-search.dto';
import NutritionShowProjection from '../DTO/entity_dto/nutritional_info/nutrition-show.projection';

@Injectable({
  providedIn: 'root',
})
export default class ProductService {
  private readonly http = inject(HttpClient);
  private readonly productAPI = new Endpoint('product');

  createProduct(product: ProductCreateDTO):Observable<number>{
    console.log(product);
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

  getAllMeasureUnits(productId: number): Observable<MeasureUnitDTO[]> {
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
