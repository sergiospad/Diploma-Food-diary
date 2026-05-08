import { Component, inject, OnInit, signal } from '@angular/core';
import DiaryRecordService from '../../../service/diary/diary-record.service';
import DiaryRecordRequest from '../../../DTO/requests/diary-record.request';
import DiaryRecordShowDTO from '../../../DTO/entity_dto/diary/diary_record/diary-record-show.dto';
import { DiaryRecord } from './diary-record/diary-record';

@Component({
  selector: 'app-food-diary',
  imports: [
    DiaryRecord
  ],
  templateUrl: './food-diary.html',
  styleUrl: './food-diary.css',
})
export class FoodDiary implements OnInit {
  private readonly diaryRecordService = inject(DiaryRecordService);

  diaryRecord = signal<DiaryRecordShowDTO>({} as DiaryRecordShowDTO);
  needsAdditional = signal<boolean>(false);
  date = signal<Date>(new Date());

  ngOnInit(): void {
    this.changeDate(new Date());
  }

  changeDate(date: Date): void {
    this.date.set(date);
    const req = {
      recordDate: this.date().toString(),
    } as DiaryRecordRequest;

    this.diaryRecordService
      .showDiaryRecord(req)
      .subscribe((res) => this.diaryRecord.set(res));
  }

  protected getAdditional() {
    this.needsAdditional.update((na) => !na);
  }
}
