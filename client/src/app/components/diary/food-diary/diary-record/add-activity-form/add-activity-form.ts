import {Component, inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import SportsActivityCreateDTO from '../../../../../DTO/entity_dto/diary/sport_activity/sports-activity-create.dto';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {AddActivityDialogData} from '../diary-record';
import SportsActivityService from '../../../../../service/diary/sports-activity.service';
import SportActivityShowDTO from '../../../../../DTO/entity_dto/diary/sport_activity/sport-activity-show.dto';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';

@Component({
  selector: 'app-add-activity-form',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatLabel
  ],
  templateUrl: './add-activity-form.html',
  styleUrl: './add-activity-form.css',
})
export class AddActivityForm{
  private readonly dialogData = inject<AddActivityDialogData>(MAT_DIALOG_DATA);
  private readonly dialogRef = inject(MatDialogRef<AddActivityForm, SportActivityShowDTO >);
  private readonly activityService = inject(SportsActivityService);

  private readonly fb = inject(FormBuilder);
  private readonly recordDate = this.dialogData.dailyRecordDate;
  readonly activityForm = this.createActivityForm();

  private createActivityForm() {
    return this.fb.group({
      "activityName": ["", Validators.required],
      "calories": [0, [Validators.required, Validators.min(1), Validators.max(5000)]],
    });
  }

  protected submitForm(): void {
    if (this.activityForm.invalid) {
      this.activityForm.markAllAsTouched();
      return;
    }

    const createActivity = {
      name: this.activityForm.controls['activityName'].value ?? '',
      calories: Number(this.activityForm.controls['calories'].value ?? 0),
      diaryRecordDate: this.recordDate
    } as SportsActivityCreateDTO;

    this.activityService.createSportActivity(createActivity)
      .subscribe((data) => this.dialogRef.close(data));
  }

}
