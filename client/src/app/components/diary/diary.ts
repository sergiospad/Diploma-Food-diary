import { Component } from '@angular/core';
import { WeightController } from './weight-controller/weight-controller';
import { TaskSection } from './task/task.section';
import { FoodDiary } from './food-diary/food-diary';

@Component({
  selector: 'app-diary',
  imports: [
    WeightController,
    TaskSection,
    FoodDiary
  ],
  templateUrl: './diary.html',
  styleUrl: './diary.css',
})
export class DiaryComponent {
}
