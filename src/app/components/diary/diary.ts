import { Component } from '@angular/core';
import {WeightController} from './weight-controller/weight-controller';
import {TaskSection} from './task/task.section';

@Component({
  selector: 'app-diary',
  imports: [
    WeightController,
    TaskSection
  ],
  templateUrl: './diary.html',
  styleUrl: './diary.css',
})
export class DiaryComponent {

}
