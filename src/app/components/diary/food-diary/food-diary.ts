import {Component, inject, OnInit, signal} from '@angular/core';
import DiaryRecordShowDTO from '../../../DTO/entity_dto/diary/diary_record/diary-record-show.dto';
import { DiaryRecord } from './diary-record/diary-record';
import DiaryRecordService from '../../../service/diary/diary-record.service';
import {DatePipe} from '@angular/common';
import DiaryRecordRequest from '../../../DTO/requests/diary-record.request';
import {CalendarDateFormatter, formatLocalCalendarDate} from '../../../util/calendar-date';

@Component({
  selector: 'app-food-diary',
  imports: [
    DiaryRecord,
    DatePipe,
    CalendarDateFormatter
  ],
  templateUrl: './food-diary.html',
  styleUrl: './food-diary.css',
})
export class FoodDiary implements OnInit {
  diaryRecordService = inject(DiaryRecordService);

  diaryRecord = signal<DiaryRecordShowDTO>({}as DiaryRecordShowDTO);
  needsAdditional = signal<boolean>(false);
  date = signal<Date>(new Date());

  ngOnInit(): void {
    this.changeDate(this.date().toString());
  }

  changeDate(date: string): void {
    this.date.set(new Date(date));
    const req = {
      recordDate: formatLocalCalendarDate(this.date()),
    }as DiaryRecordRequest;
    this.diaryRecordService.showDiaryRecord(req)
      .subscribe((res: DiaryRecordShowDTO) => this.diaryRecord.set(res))
  }

  protected getAdditional() {
    this.needsAdditional.update((na) => !na);
  }
}
