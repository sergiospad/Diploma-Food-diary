import {Component, inject, OnInit, signal} from '@angular/core';
import {BmiDiagram} from './bmi-diagramm/bmi-diagram';
import CurrentWeightRecordShowDTO from '../../../DTO/entity_dto/diary/weight_record/current-weight-record-show.dto';
import WeightRecordService from '../../../service/diary/weight-record.service';
import {AddWeightRecord, WeightRecordDialogData} from './add-weight-record/add-weight-record';
import {WeightInfo} from './weight-info/weight-info';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';
import {WeightChartComponent} from './weight-chart/weight-chart';

@Component({
  selector: 'app-weight-controller',
  imports: [BmiDiagram, WeightInfo, MatButton, WeightChartComponent],
  templateUrl: './weight-controller.html',
  styleUrl: './weight-controller.css',
})
export class WeightController implements OnInit {
  weightService = inject(WeightRecordService);
  dialog = inject(MatDialog);
  weightRecord = signal<CurrentWeightRecordShowDTO>({}as CurrentWeightRecordShowDTO);

  ngOnInit(): void {
    this.weightService.getCurrentWeightRecord()
      .subscribe(data=> this.weightRecord.set(data));
  }

  openRecordForm(){
    const cfg = new MatDialogConfig<WeightRecordDialogData>();
    cfg.width = '900px';
    cfg.data = {
      record: this.weightRecord().measure ?? null,
    };
    this.dialog
      .open(AddWeightRecord, cfg)
      .afterClosed()
      .subscribe((updated:CurrentWeightRecordShowDTO) => {
        if (updated) {
          this.weightRecord.set(updated);
        }
      });
  }

}
