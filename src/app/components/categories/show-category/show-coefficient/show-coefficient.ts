import {Component, computed, inject,  model, OnInit, signal} from '@angular/core';
import CoefficientShowDTO from '../../../../DTO/entity_dto/recipe-recource/coefficient/coefficient-show.dto';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-show-coefficient',
  imports: [
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatFormField,
    MatError,
  ],
  templateUrl: './show-coefficient.html',
  styleUrl: './show-coefficient.css',
})
export class ShowCoefficient implements OnInit {
  coefficient = model.required<CoefficientShowDTO>();
  amountSig = signal<number>(1);
  protected amountForm!: FormGroup;
  protected fb = inject(FormBuilder);

  protected scaledAmount = computed(() =>
    this.amountSig() * this.coefficient().conversionFactor,
  );

  ngOnInit(): void {
    this.amountForm = this.fb.group({
      amount: [1, [Validators.required, Validators.min(1)]],
    });
    this.amountForm.controls['amount'].valueChanges.subscribe((v) => {
      const n = v === '' || v == null ? NaN : Number(v);
      if (!Number.isNaN(n)) {
        this.amountSig.set(n);
      }
    });

  }
}
