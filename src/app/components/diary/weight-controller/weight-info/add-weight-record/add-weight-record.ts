import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import WeightRecordCreateDTO from '../../../../../DTO/entity_dto/diary/weight_record/weight-record-create.dto';
import WeightRecordService from '../../../../../service/diary/weight-record.service';
import UserProfileValidator from '../../../../user-profile/profile.validator';

export interface WeightRecordDialogData {
  record: number;
}

@Component({
  selector: 'app-add-weight-record',
  imports: [],
  templateUrl: './add-weight-record.html',
  styleUrl: './add-weight-record.css',
})
export class AddWeightRecord implements OnInit {
  private readonly weightRecordService = inject(WeightRecordService);
  private readonly dialogData = inject<WeightRecordDialogData | undefined>(MAT_DIALOG_DATA);
  private readonly dialogRef = inject(MatDialogRef<AddWeightRecord, WeightRecordDialogData | undefined>);
  private dates: Date[] = [];

  private readonly fb = inject(FormBuilder);
  protected recordForm!: FormGroup;

  ngOnInit(): void {
    const initialWeight = this.dialogData?.record ?? 70;
    this.recordForm = this.fb.group({
      weightRecord: new FormControl(initialWeight, [
        Validators.required,
        Validators.min(30),
        Validators.max(200),
      ]),
      date: new FormControl(null, [Validators.required, UserProfileValidator.birthdateValidator]),
    });
    this.weightRecordService.getMeasurementDates().subscribe((data) => {
      this.dates = data.datesOfMeasurement;
    });
  }

  protected ableToSubmit(): boolean {
    if (!this.recordForm.valid) {
      return false;
    }
    return this.dates.includes(this.recordForm.controls['date'].value);
  }

  protected submitRecord(): void {
    const record = {
      measure: this.recordForm.controls['weightRecord'].value,
      dateOfMeasurement: this.recordForm.controls['date'].value,
    } as WeightRecordCreateDTO;
    this.weightRecordService.createRecord(record).subscribe((data) => {
      this.dialogRef?.close(data);
    });
  }
}
