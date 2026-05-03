import {Component, inject, OnInit, signal} from '@angular/core';
import {BmiDiagram} from './bmi-diagramm/bmi-diagram';
import CurrentWeightRecordShowDTO from '../../../DTO/entity_dto/diary/weight_record/current-weight-record-show.dto';
import WeightRecordService from '../../../service/diary/weight-record.service';
import {AddWeightRecord} from './weight-info/add-weight-record/add-weight-record';

@Component({
  selector: 'app-weight-controller',
  imports: [BmiDiagram, AddWeightRecord],
  templateUrl: './weight-controller.html',
  styleUrl: './weight-controller.css',
})
export class WeightController implements OnInit {
  weightService = inject(WeightRecordService);
  weightRecord = signal<CurrentWeightRecordShowDTO>({}as CurrentWeightRecordShowDTO);

  ngOnInit(): void {
    this.weightService.getCurrentWeightRecord()
      .subscribe(data=> this.weightRecord.set(data));
  }

}
