import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import CookingStageEditDescDTO from '../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-edit-desc.dto';
import {Observable} from 'rxjs';
import CookingStageShowDTO from '../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-show.dto';

@Injectable({
  providedIn: 'root',
})
export default class CookingStageService {
  private readonly http = inject(HttpClient);
  private readonly cookingStageAPI = new Endpoint('stage');

  editCookingStage(stage:CookingStageEditDescDTO):Observable<any>{
    return this.http.patch<any>(
      this.cookingStageAPI.builder().
      points("edit")
        .build(),
      stage);
  }

  getAllShowDTO(id:number):Observable<CookingStageShowDTO[]>{
    return this.http.get<CookingStageShowDTO[]>(
      this.cookingStageAPI.builder()
        .points("all")
        .addParam("id", id.toString())
    .build())
  }

  deleteCookingStage(id:number):Observable<any>{
    return this.http.delete<any>(
      this.cookingStageAPI.builder()
        .points("delete")
        .addParam("id", id.toString())
        .build())
  }
}
