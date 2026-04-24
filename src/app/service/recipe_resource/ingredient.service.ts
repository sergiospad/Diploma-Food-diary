import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import {Observable} from 'rxjs';
import IngredientShowDTO from '../../DTO/entity_dto/recipe-recource/ingredient/ingredient-show.dto';
import IngredientChangeDTO from '../../DTO/entity_dto/recipe-recource/ingredient/ingredient-change.dto';

@Injectable({
  providedIn: 'root',
})
export default class IngredientService {
  private readonly http = inject(HttpClient);
  private readonly ingredientAPI = new Endpoint('ingredient');

  removeIngredient(id: number):Observable<any>{
    return this.http.delete(
      this.ingredientAPI.builder()
        .points("delete", id.toString())
        .build());
  }

  getShowIngredients(id:number):Observable<IngredientShowDTO[]>{
    return this.http.get<IngredientShowDTO[]>(
      this.ingredientAPI.builder()
        .points("get", id.toString())
        .build());
  }

  toggleMeasureUnit(ingredient:IngredientChangeDTO):Observable<IngredientShowDTO>{
    return this.http.post<IngredientShowDTO>(
      this.ingredientAPI.builder()
        .points("toggle")
        .build(),
      ingredient)
  }
}
