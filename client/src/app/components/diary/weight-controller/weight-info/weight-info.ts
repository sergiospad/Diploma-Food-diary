import {DatePipe, DecimalPipe} from '@angular/common';
import {Component, model} from '@angular/core';
import CurrentWeightRecordShowDTO from '../../../../DTO/entity_dto/diary/weight_record/current-weight-record-show.dto';

@Component({
  selector: 'app-weight-info',
  imports: [DatePipe, DecimalPipe],
  templateUrl: './weight-info.html',
  styleUrl: './weight-info.css',
})
export class WeightInfo {
  weightRecord = model.required<CurrentWeightRecordShowDTO|null>();

}
