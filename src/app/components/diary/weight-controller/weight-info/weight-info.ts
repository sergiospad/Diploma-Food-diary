import {Component, inject, model} from '@angular/core';
import CurrentWeightRecordShowDTO from '../../../../DTO/entity_dto/diary/weight_record/current-weight-record-show.dto';
import {FormBuilder} from '@angular/forms';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {
  AddCoefficient,
} from '../../../categories/show-category/show-coefficient/add-coefficient/add-coefficient';
import {AddWeightRecord} from './add-weight-record/add-weight-record';

export interface WeightRecordDialogData{
  weightRecord?: number
}

@Component({
  selector: 'app-weight-info',
  imports: [],
  templateUrl: './weight-info.html',
  styleUrl: './weight-info.css',
})
export class WeightInfo {
  weightRecord = model.required<CurrentWeightRecordShowDTO>();
  dialog = inject(MatDialog);

  openRecordForm(){
    const cfg = new MatDialogConfig<WeightRecordDialogData>();
    cfg.width = '900px';
    cfg.data = {
      weightRecord: this.weightRecord().measure,
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
