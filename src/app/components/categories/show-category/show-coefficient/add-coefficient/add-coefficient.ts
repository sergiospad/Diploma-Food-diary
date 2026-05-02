import {Component, inject, input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import MeasureUnitService from '../../../../../service/recipe_resource/measure-unit.service';
import MeasureUnitDTO from '../../../../../DTO/entity_dto/recipe-recource/measure-unit.dto';
import {MatOption} from '@angular/material/core';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatSelect} from '@angular/material/select';

@Component({
  selector: 'app-add-coefficient',
  imports: [
    FormsModule,
    MatOption,
    MatFormField,
    MatLabel,
    MatSelect,
    ReactiveFormsModule,
    MatError,
    MatInput
  ],
  templateUrl: './add-coefficient.html',
  styleUrl: './add-coefficient.css',
})
export class AddCoefficient implements OnInit {
  measureUnitService = inject(MeasureUnitService);
  fb=inject(FormBuilder);

  categoryID = input.required<number>();

  freeUnits: MeasureUnitDTO[]=[];
  coeffForm!: FormGroup;


  ngOnInit(): void {
    this.coeffForm = this.fb.group({
      muAmount: [1, [Validators.required, Validators.min(0.0001), Validators.max(10000)] ],
      muID:[null, Validators.required],
      gAmount: [1, [Validators.required, Validators.min(0.0001), Validators.max(10000)] ]
    })
    this.measureUnitService.findFreeMeasureUnit(this.categoryID())
      .subscribe(measureUnits => this.freeUnits = measureUnits);
  }


  protected onSelectChange(value:number) {
    const name = this.freeUnits.find(f => f.id === value)?.name;
    if(name=="кг")
      this.coeffForm.controls['gAmount'].patchValue(this.coeffForm.controls['muAmount'].value*1000);
  }
}
