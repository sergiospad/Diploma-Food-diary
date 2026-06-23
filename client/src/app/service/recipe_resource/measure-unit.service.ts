import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import {Observable} from 'rxjs';
import MeasureUnitDTO from '../../DTO/entity_dto/recipe-recource/measure-unit.dto';

@Injectable({
  providedIn: 'root',
})
export default class MeasureUnitService {
  private readonly http = inject(HttpClient);
  private readonly measureUnitAPI = new Endpoint('measureUnit');

  findAllMeasureUnit(id: number): Observable<MeasureUnitDTO[]> {
    return this.http.get<MeasureUnitDTO[]>(
      this.measureUnitAPI.builder()
        .points("all", id.toString())
        .build())
  }

  createMeasureUnit(name: string):Observable<MeasureUnitDTO>{
    return this.http.get<MeasureUnitDTO>(
      this.measureUnitAPI.builder()
        .points("create")
        .addParam("name", name)
        .build())
  }

  findAll():Observable<MeasureUnitDTO[]>{
    return this.http.get<MeasureUnitDTO[]>(
      this.measureUnitAPI.builder()
        .points("all")
        .build())
  }

  findFreeMeasureUnit(id:number):Observable<MeasureUnitDTO[]>{
    return this.http.get<MeasureUnitDTO[]>(
      this.measureUnitAPI.builder()
        .points("free", id.toString())
        .build())
  }
}
