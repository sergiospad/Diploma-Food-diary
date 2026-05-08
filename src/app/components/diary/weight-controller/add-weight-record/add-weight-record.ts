import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import WeightRecordCreateDTO from '../../../../DTO/entity_dto/diary/weight_record/weight-record-create.dto';
import WeightRecordService from '../../../../service/diary/weight-record.service';
import UserProfileValidator from '../../../user-profile/profile.validator';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';

export interface WeightRecordDialogData {
  record: number|null;
}

@Component({
  selector: 'app-add-weight-record',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatButton,
    MatError
  ],
  templateUrl: './add-weight-record.html',
  styleUrl: './add-weight-record.css',
})
export class AddWeightRecord implements OnInit {
  private readonly weightRecordService = inject(WeightRecordService);
  /** Present only when this component is opened with MatDialog; omitted when embedded (e.g. weight-controller). */
  private readonly dialogData = inject(MAT_DIALOG_DATA, {optional: true}) as
    | WeightRecordDialogData
    | undefined;
  private readonly dialogRef = inject(MatDialogRef<AddWeightRecord>, {optional: true});
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

  protected hasDuplicateMeasurementDate(): boolean {
    const selected = this.recordForm.controls['date'].value;
    if (!selected) {
      return false;
    }

    const selectedDate = this.toDateOnlyIso(selected);
    return this.dates.some((d) => this.toDateOnlyIso(d) === selectedDate);
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

  private toDateOnlyIso(value: Date | string): string {
    if (value instanceof Date) {
      return value.toISOString().slice(0, 10);
    }

    // Value from <input type="date"> is already in YYYY-MM-DD format.
    return value;
  }
}
