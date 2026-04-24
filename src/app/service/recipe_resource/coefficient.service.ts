import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import CoefficientEditDTO from '../../DTO/entity_dto/recipe-recource/coefficient/coefficient-edit.dto';
import {Observable} from 'rxjs';
import CoefficientShowDTO from '../../DTO/entity_dto/recipe-recource/coefficient/coefficient-show.dto';

@Injectable({
  providedIn: 'root',
})
export class CoefficientService {
  private readonly http = inject(HttpClient);
  private readonly categoryAPI = new Endpoint('coefficient');

  editCoefficient(coefficient:CoefficientEditDTO):Observable<CoefficientShowDTO>{
    return this.http.patch<CoefficientShowDTO>(
      this.categoryAPI.builder()
        .points("edit")
        .build(),
      coefficient)
  }

}
