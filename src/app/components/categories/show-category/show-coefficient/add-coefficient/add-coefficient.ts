import {Component, DestroyRef, inject, OnInit} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatOption} from '@angular/material/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatSelect} from '@angular/material/select';
import CategoryAddCoefficientDTO from '../../../../../DTO/entity_dto/recipe-recource/category/category-add-coefficient.dto';
import CategoryShowDto from '../../../../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import MeasureUnitDTO from '../../../../../DTO/entity_dto/recipe-recource/measure-unit.dto';
import {NotificationService} from '../../../../../security/notification-service';
import CategoryService from '../../../../../service/recipe_resource/category.service';
import MeasureUnitService from '../../../../../service/recipe_resource/measure-unit.service';

export interface AddCoefficientDialogData {
  categoryId: number;
}

@Component({
  selector: 'app-add-coefficient',
  imports: [
    ReactiveFormsModule,
    MatOption,
    MatFormField,
    MatLabel,
    MatSelect,
    MatError,
    MatInput,
    MatButton,
  ],
  templateUrl: './add-coefficient.html',
  styleUrl: './add-coefficient.css',
})
export class AddCoefficient implements OnInit {
  private readonly measureUnitService = inject(MeasureUnitService);
  private readonly categoryService = inject(CategoryService);
  private readonly dialogRef = inject(MatDialogRef<AddCoefficient, CategoryShowDto | undefined>);
  private readonly dialogData = inject<AddCoefficientDialogData>(MAT_DIALOG_DATA);
  private readonly destroyRef = inject(DestroyRef);
  private readonly notificationService = inject(NotificationService);
  private readonly fb = inject(FormBuilder);

  protected freeUnits: MeasureUnitDTO[] = [];
  protected coeffForm!: FormGroup;

  ngOnInit(): void {
    this.coeffForm = this.fb.group({
      muAmount: [1, [Validators.required, Validators.min(0.0001), Validators.max(10000)]],
      measureUnitId: [null as number | null, Validators.required],
      gAmount: [
        1,
        [Validators.required, Validators.min(0.0001), Validators.max(10_000_000)],
      ],
    });

    this.measureUnitService
      .findFreeMeasureUnit(this.dialogData.categoryId)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((units) => (this.freeUnits = units));

    this.coeffForm.controls['muAmount'].valueChanges
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => this.syncGramsForKg());

    this.coeffForm.controls['measureUnitId'].valueChanges
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((id) => this.onMeasureUnitChange(id));
  }

  protected cancel(): void {
    this.dialogRef.close();
  }

  protected submit(): void {
    if (this.coeffForm.invalid) {
      return;
    }
    const v = this.coeffForm.getRawValue();
    const mu = Number(v.muAmount);
    const g = Number(v.gAmount);
    if (Number.isNaN(mu) || Number.isNaN(g) || mu === 0) {
      return;
    }
    const conversionFactor = g / mu;
    const dto: CategoryAddCoefficientDTO = {
      id: this.dialogData.categoryId,
      coefficients: [
        {
          measureUnitId: v.measureUnitId as number,
          conversionFactor,
        },
      ],
    };
    this.categoryService.addCoefficient(dto).subscribe({
      next: (updated) => this.dialogRef.close(updated),
      error: () => this.notificationService.showSnackBar('Не удалось добавить коэффициент'),
    });
  }

  private onMeasureUnitChange(unitId: number | null): void {
    if (unitId == null) {
      return;
    }
    this.syncGramsForKg();
  }

  private syncGramsForKg(): void {
    const id = this.coeffForm.controls['measureUnitId'].value as number | null;
    if (id == null) {
      return;
    }
    const unit = this.freeUnits.find((u) => u.id === id);
    if (unit?.name !== 'кг') {
      return;
    }
    const muAmt = Number(this.coeffForm.controls['muAmount'].value);
    if (Number.isNaN(muAmt)) {
      return;
    }
    this.coeffForm.controls['gAmount'].patchValue(muAmt * 1000, {emitEvent: false});
  }
}
