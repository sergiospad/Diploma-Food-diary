import { Component } from '@angular/core';
import {WeightController} from './weight-controller/weight-controller';

@Component({
  selector: 'app-diary',
  imports: [
    WeightController
  ],
  templateUrl: './diary.html',
  styleUrl: './diary.css',
})
export class DiaryComponent {

}
