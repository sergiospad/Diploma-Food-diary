import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import DiaryRecordRequest from '../../DTO/requests/diary-record.request';
import DiaryRecordShowDTO from '../../DTO/entity_dto/diary/diary_record/diary-record-show.dto';
import CalorieConsumptionShowDTO from '../../DTO/entity_dto/diary/sport_activity/calorie-consumption-show.dto';
import type { CalendarDate } from '../../DTO/types';
import { formatLocalCalendarDate } from '../../util/calendar-date';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DiaryRecordService {
  private readonly http = inject(HttpClient);
  private readonly diaryAPI = new Endpoint('diary');

  createDiaryRecord(request: DiaryRecordRequest):Observable<any>{
    return this.http.put(
      this.diaryAPI.builder()
        .points("create")
        .build(),
      request)
  }

  showDiaryRecord(request: DiaryRecordRequest):Observable<DiaryRecordShowDTO>{
    return this.http.post<DiaryRecordShowDTO>(
      this.diaryAPI.builder()
        .points("show")
        .build(),
      request)
  }

  getConsumptionOfDiaryRecord(
    date: CalendarDate | Date,
  ): Observable<CalorieConsumptionShowDTO> {
    const dateParam =
      typeof date === 'string' ? date : formatLocalCalendarDate(date);
    return this.http.get<CalorieConsumptionShowDTO>(
      this.diaryAPI.builder()
        .points("consumption")
        .addParam("date", encodeURIComponent(dateParam))
        .build(),
    );
  }
}

