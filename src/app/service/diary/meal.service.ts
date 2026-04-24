import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import MealShowDTO from '../../DTO/entity_dto/diary/meal/meal-show.dto';
import {Observable} from 'rxjs';
import MealCreateDTO from '../../DTO/entity_dto/diary/meal/meal-create.dto';
import MealEditDTO from '../../DTO/entity_dto/diary/meal/meal-edit.dto';

@Injectable({
  providedIn: 'root',
})
export default class MealService {
  private readonly http = inject(HttpClient);
  private readonly mealAPI = new Endpoint('meal');

  createMeal(meal:MealCreateDTO):Observable<MealShowDTO>{
    return this.http.put<MealShowDTO>(
      this.mealAPI.builder()
        .points("create")
        .build(),
      meal)
  }

  editMeal(meal: MealEditDTO):Observable<MealShowDTO>{
    return this.http.patch<MealShowDTO>(
      this.mealAPI.builder()
        .points("edit")
        .build(),
      meal)
  }
}
