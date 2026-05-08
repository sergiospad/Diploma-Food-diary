import { Component, inject, input, model } from '@angular/core';
import DiaryRecordShowDTO from '../../../../DTO/entity_dto/diary/diary_record/diary-record-show.dto';
import { CalendarDate, MealType } from '../../../../DTO/types';
import MealShowDTO from '../../../../DTO/entity_dto/diary/meal/meal-show.dto';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddMeal } from './add-meal/add-meal';

export interface AddMealDialogData {
  meals: MealShowDTO[];
  dailyRecordDate: CalendarDate;
}

export const mealMap = new Map<MealType, string>([
  ['BREAKFAST', 'Завтрак'],
  ['LUNCH', 'Ланч'],
  ['DINNER', 'Обед'],
  ['AFTERNOON_SNACK', 'Полдник'],
  ['SUPPER', 'Ужин'],
  ['EVENING_SNACK', 'Вечерний перекус'],
]);

@Component({
  selector: 'app-diary-record',
  imports: [],
  templateUrl: './diary-record.html',
  styleUrl: './diary-record.css',
})
export class DiaryRecord {
  dialog = inject(MatDialog);

  diaryRecord = model.required<DiaryRecordShowDTO>();
  needsAdditional = input.required<boolean>();
  readonly mealMap = new Map<MealType, string>([
    ['BREAKFAST', 'Завтрак'],
    ['LUNCH', 'Ланч'],
    ['DINNER', 'Обед'],
    ['AFTERNOON_SNACK', 'Полдник'],
    ['SUPPER', 'Ужин'],
    ['EVENING_SNACK', 'Вечерний перекус'],
  ]);

  protected addMeal() {
    const cfg = new MatDialogConfig<AddMealDialogData>();
    cfg.width = '1200px';
    cfg.data = {
      meals: this.diaryRecord().meals,
      dailyRecordDate: this.diaryRecord().date,
    };
    this.dialog
      .open(AddMeal, cfg)
      .afterClosed()
      .subscribe((updated: MealShowDTO) => {
        if (updated) {
          this.diaryRecord.update((rec) => ({
            ...rec,
            meals: [...rec.meals, updated],
          }));
        }
      });
  }
}
