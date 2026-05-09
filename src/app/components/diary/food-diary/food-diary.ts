import { Component, OnInit, signal } from '@angular/core';
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
  private readonly mockDiaryRecord: DiaryRecordShowDTO = {
    date: '2026-05-08',
    meals: [
      {
        id: 1,
        time: '08:20',
        mealType: 'BREAKFAST',
        calories: 520,
        protein: 27,
        fat: 18,
        carbs: 58,
        showDTOList: [
          {
            id: 101,
            name: 'Овсянка с ягодами',
            productWeight: 250,
            nutritionType: 'RECIPE',
            calories: 310,
            protein: 12,
            fat: 8,
            carbs: 49,
          },
          {
            id: 102,
            name: 'Яйца вареные',
            productWeight: 100,
            nutritionType: 'PRODUCT',
            calories: 210,
            protein: 15,
            fat: 10,
            carbs: 9,
          },
        ],
      },
      {
        id: 2,
        time: '13:10',
        mealType: 'DINNER',
        calories: 740,
        protein: 46,
        fat: 24,
        carbs: 82,
        showDTOList: [
          {
            id: 201,
            name: 'Курица запеченная',
            productWeight: 220,
            nutritionType: 'PRODUCT',
            calories: 350,
            protein: 38,
            fat: 14,
            carbs: 0,
          },
          {
            id: 202,
            name: 'Рис с овощами',
            productWeight: 280,
            nutritionType: 'RECIPE',
            calories: 390,
            protein: 8,
            fat: 10,
            carbs: 82,
          },
        ],
      },
      {
        id: 3,
        time: '19:40',
        mealType: 'SUPPER',
        calories: 430,
        protein: 31,
        fat: 15,
        carbs: 42,
        showDTOList: [
          {
            id: 301,
            name: 'Творог 5%',
            productWeight: 200,
            nutritionType: 'PRODUCT',
            calories: 250,
            protein: 28,
            fat: 10,
            carbs: 8,
          },
          {
            id: 302,
            name: 'Яблоко',
            productWeight: 180,
            nutritionType: 'PRODUCT',
            calories: 180,
            protein: 3,
            fat: 5,
            carbs: 34,
          },
        ],
      },
    ],
    total: {
      calories: 1690,
      protein: 104,
      fat: 57,
      carbs: 182,
    },
    calorieConsumption: {
      inRestConsumption: 1850,
      autoCalc: true,
      sportActivityShowDTOList: [],
    },
  };

  diaryRecord = signal<DiaryRecordShowDTO>(this.mockDiaryRecord);
  needsAdditional = signal<boolean>(false);
  date = signal<string>(this.mockDiaryRecord.date);

  ngOnInit(): void {
    this.changeDate(this.mockDiaryRecord.date);
  }

  changeDate(date: string): void {
    this.date.set(date);
    this.diaryRecord.set({
      ...this.mockDiaryRecord,
      date,
    });
  }

  protected getAdditional() {
    this.needsAdditional.update((na) => !na);
  }
}
