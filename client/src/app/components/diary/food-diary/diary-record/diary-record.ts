import {Component, computed, inject, input, model} from '@angular/core';
import DiaryRecordShowDTO from '../../../../DTO/entity_dto/diary/diary_record/diary-record-show.dto';
import { CalendarDate, MealType } from '../../../../DTO/types';
import MealShowDTO from '../../../../DTO/entity_dto/diary/meal/meal-show.dto';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddMealForm } from './add-meal-form/add-meal-form';
import {formatLocalCalendarDate} from '../../../../util/calendar-date';
import {DecimalPipe} from '@angular/common';
import {AddActivityForm} from './add-activity-form/add-activity-form';
import SportActivityShowDTO from '../../../../DTO/entity_dto/diary/sport_activity/sport-activity-show.dto';

export interface AddActivityDialogData {
  dailyRecordDate: CalendarDate;
}
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
  imports: [
    DecimalPipe
  ],
  templateUrl: './diary-record.html',
  styleUrl: './diary-record.css',
})
export class DiaryRecord {
  dialog = inject(MatDialog);

  diaryRecord = model.required<DiaryRecordShowDTO>();
  needsAdditional = input.required<boolean>();

  totalActivity = computed(()=>{
    const activities = this.diaryRecord()?.calorieConsumption?.sportActivityShowDTOList ?? [];
    return activities.reduce((sum, activity) => sum + (activity.calories ?? 0), 0);
  });

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
    cfg.maxWidth = '95vw'
    cfg.data = {
      meals: this.diaryRecord().meals,
      dailyRecordDate: this.diaryRecord().date,
    };
    this.dialog
      .open(AddMealForm, cfg)
      .afterClosed()
      .subscribe((updated: MealShowDTO[]) => {
        if (updated) {
          this.diaryRecord.update((rec) => ({
            ...rec,
            meals: [...updated],
          }));
        }
      });
  }


  protected getPassiveCaloriesCons = computed(()=> {
    const d =  this.diaryRecord()?.calorieConsumption?.inRestConsumption??0
    if(this.diaryRecord().date == formatLocalCalendarDate(new Date())){
      const thisDate = new Date();
      const startTime = new Date(thisDate.getFullYear(), thisDate.getMonth(), thisDate.getDate()).getTime();
      const endDayTime = new Date(thisDate.getFullYear(), thisDate.getMonth(), thisDate.getDate()-1).getTime();
      const endTime = Date.now();
      return (endTime - startTime)/(startTime - endDayTime)*d;
    }
    return d;
  });

    protected getDeficit = computed(()=> {
      const consumedCalories = this.diaryRecord().total?.calories ?? 0;
      return this.getPassiveCaloriesCons() - consumedCalories;
    })
  protected readonly Math = Math;

  protected addActivity() {
    const cfg = new MatDialogConfig<AddActivityDialogData>();
    cfg.width = '800px';

    cfg.data = {
      dailyRecordDate: this.diaryRecord().date,
    };
    this.dialog
      .open(AddActivityForm, cfg)
      .afterClosed()
      .subscribe((updated: SportActivityShowDTO) => {
        if (updated) {
          this.diaryRecord.update((rec) => ({
            ...rec,
            calorieConsumption: {
              ...rec.calorieConsumption!,
              sportActivityShowDTOList: [
                ...(rec.calorieConsumption?.sportActivityShowDTOList ?? []),
                updated,
              ],
            },
          }));
        }
      });
  }
}
