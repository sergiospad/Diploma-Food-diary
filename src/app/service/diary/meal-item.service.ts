import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import MealItemEditDTO from '../../DTO/entity_dto/diary/meal_item/meal-item-edit.dto';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export default class MealItemService {
  private readonly http = inject(HttpClient);
  private readonly mealItemAPI = new Endpoint('mealItem');

  updateMealItem(mealItem: MealItemEditDTO):Observable<any>{
    return this.http.patch(this.mealItemAPI.builder()
        .points("edit")
        .build(),
      mealItem);
  }

  deleteMealItem(id:number):Observable<any>{
    return this.http.delete(
      this.mealItemAPI.builder()
        .points("delete", id.toString())
        .build());
  }
}
