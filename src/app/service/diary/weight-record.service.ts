import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import {Observable} from 'rxjs';
import WeightRecordShowDTO from '../../DTO/entity_dto/diary/weight_record/weight-record-show.dto';
import CurrentWeightRecordShowDTO from '../../DTO/entity_dto/diary/weight_record/current-weight-record-show.dto';
import WeightRecordCreateDTO from '../../DTO/entity_dto/diary/weight_record/weight-record-create.dto';
import WeightChartRequest from '../../DTO/requests/weight-chart.request';
import WeightChartDataDTO from '../../DTO/entity_dto/diary/weight_record/weight-chart-data.dto';

@Injectable({
  providedIn: 'root',
})
export default class WeightRecordService {
  private readonly http = inject(HttpClient);
  private readonly recordAPI = new Endpoint('weightRecord');

  getAllWeightRecords():Observable<WeightRecordShowDTO[]>{
    return this.http.get<WeightRecordShowDTO[]>(
      this.recordAPI.builder()
        .points("all")
        .build())
  }

  getCurrentWeightRecord():Observable<CurrentWeightRecordShowDTO>{
    return this.http.get<CurrentWeightRecordShowDTO>(
      this.recordAPI.builder()
        .points("current")
        .build());
  }

  createRecord(record: WeightRecordCreateDTO): Observable<CurrentWeightRecordShowDTO> {
    return this.http.put<CurrentWeightRecordShowDTO>(
      this.recordAPI.builder()
        .points("create")
        .build(),
      record);
  }

  createChart(record: WeightChartRequest):Observable<WeightChartDataDTO>{
    return this.http.post<WeightChartDataDTO>(
      this.recordAPI.builder()
        .points("chart")
        .build(),
      record);
  }
}
