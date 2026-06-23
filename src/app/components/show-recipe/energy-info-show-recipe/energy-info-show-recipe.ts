import {Component, effect, input} from '@angular/core';
import EnergyValueShowDTO from '../../../DTO/entity_dto/nutritional_info/energy-value-show.dto';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {FormsModule} from '@angular/forms';
import {CaloricityType} from '../../../DTO/types';
import BaseNutrition from '../../../DTO/entity_dto/nutritional_info/base-nurtition';
import {ChartData, ChartOptions} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import {DecimalPipe, PercentPipe} from '@angular/common';

@Component({
  selector: 'app-energy-info-show-recipe',
  imports: [
    MatOption,
    MatSelect,
    FormsModule,
    BaseChartDirective,
    DecimalPipe,
    PercentPipe,
  ],
  templateUrl: './energy-info-show-recipe.html',
  styleUrl: './energy-info-show-recipe.css',
})
export class EnergyInfoShowRecipe {
  energyInfo = input<EnergyValueShowDTO | undefined>();

  protected readonly pieChartType = 'pie' as const;
  protected pieChartData: ChartData<'pie', number[], string> = {
    labels: ['Белки', 'Жиры', 'Углеводы'],
    datasets: [
      {
        data: [0, 0, 0],
        backgroundColor: ['#42A5F5', '#FFA726', '#66BB6A'],
      },
    ],
  };
  pieChartOptions: ChartOptions<'pie'> = {
    responsive: true,
    plugins: {
      legend: { position: 'right' },
      tooltip: {
        enabled: true,
      },
    },
  };

  protected readonly typeDict = new Map<CaloricityType, string>([
    ['PER_HUNDRED', 'На 100 г'],
    ['ALL', 'Общая'],
  ]);
  protected readonly types: CaloricityType[] = ['PER_HUNDRED', 'ALL'];
  protected baseNutrition = {} as BaseNutrition;

  protected selectedType: CaloricityType = 'PER_HUNDRED';

  constructor() {
    effect(() => {
      const info = this.energyInfo();
      if (!info) {
        return;
      }
      const weight = info.protein + info.fat + info.carbs;
      if (weight > 0) {
        info.proteinPer = info.protein / weight;
        info.fatPer = info.fat / weight;
        info.carbsPer = info.carbs / weight;
      }
      this.selectedType = info.caloricityType;
      this.applyCaloricityType(info.caloricityType);
    });
  }

  protected onTypeChange(value: CaloricityType): void {
    const info = this.energyInfo();
    if (info) {
      info.caloricityType = value;
    }
    this.applyCaloricityType(value);
  }

  private applyCaloricityType(t: CaloricityType): void {
    const info = this.energyInfo();
    if (!info) {
      return;
    }
    if (t === 'PER_HUNDRED') {
      this.multiplyBaseNutrition(1);
    } else {
      const coeff = info.productWeight / 100;
      this.multiplyBaseNutrition(coeff);
    }
  }

  private multiplyBaseNutrition(coeff: number): void {
    const info = this.energyInfo();
    if (!info) {
      return;
    }
    this.baseNutrition.calories = info.calories * coeff;
    this.baseNutrition.proteins = info.protein * coeff;
    this.baseNutrition.fat = info.fat * coeff;
    this.baseNutrition.carbs = info.carbs * coeff;
    this.setChartData();
  }

  private setChartData(): void {
    this.pieChartData = {
      labels: ['Белки', 'Жиры', 'Углеводы'],
      datasets: [
        {
          data: [
            this.baseNutrition.proteins,
            this.baseNutrition.fat,
            this.baseNutrition.carbs,
          ],
          backgroundColor: ['#42A5F5', '#FFA726', '#66BB6A'],
        },
      ],
    };
  }
}
