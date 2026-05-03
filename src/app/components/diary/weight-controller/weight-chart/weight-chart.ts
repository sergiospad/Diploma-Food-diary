import {DecimalPipe} from '@angular/common';
import {Component, inject, OnInit, signal} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ChartData, ChartOptions} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import WeightChartDataDTO from '../../../../DTO/entity_dto/diary/weight_record/weight-chart-data.dto';
import WeightChartRequest from '../../../../DTO/requests/weight-chart.request';
import {ChartPeriodType} from '../../../../DTO/types';
import WeightRecordService from '../../../../service/diary/weight-record.service';

@Component({
  selector: 'app-weight-chart',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatButton,
    BaseChartDirective,
    DecimalPipe,
  ],
  templateUrl: './weight-chart.html',
  styleUrl: './weight-chart.css',
})
export class WeightChartComponent implements OnInit {
  protected readonly lineChartType = 'line' as const;
  protected readonly weightChart = signal<WeightChartDataDTO>(this.emptyChartDto());
  protected readonly chartHydrated = signal(false);

  protected readonly chartPeriodOptions: ReadonlyArray<{value: ChartPeriodType; label: string}> = [
    {value: 'DAY', label: 'День'},
    {value: 'WEEK', label: 'Неделя'},
    {value: 'MONTH', label: 'Месяц'},
    {value: 'YEAR', label: 'Год'},
  ];

  protected lineChartData: ChartData<'line'> = {
    labels: [],
    datasets: [
      {
        label: 'Вес, кг',
        data: [],
        fill: true,
        tension: 0.25,
        borderColor: '#443737',
        backgroundColor: 'rgba(251, 183, 49, 0.14)',
        pointRadius: 4,
        pointHoverRadius: 6,
        pointBackgroundColor: '#443737',
        pointBorderColor: '#ffffff',
        pointBorderWidth: 2,
      },
    ],
  };

  protected lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
    maintainAspectRatio: false,
    interaction: {intersect: false, mode: 'index'},
    scales: {
      x: {
        title: {display: true, text: 'Дата', color: '#6e5e5e', font: {size: 11, weight: 600}},
        ticks: {maxRotation: 45, minRotation: 0, color: '#6e5e5e'},
        grid: {color: 'rgba(68, 55, 55, 0.06)'},
      },
      y: {
        title: {display: true, text: 'кг', color: '#6e5e5e', font: {size: 11, weight: 600}},
        ticks: {color: '#6e5e5e'},
        grid: {color: 'rgba(68, 55, 55, 0.08)'},
      },
    },
    plugins: {
      legend: {
        display: true,
        labels: {color: '#443737', font: {weight: 500}},
      },
      tooltip: {
        enabled: true,
        backgroundColor: 'rgba(68, 55, 55, 0.92)',
        titleColor: '#fff',
        bodyColor: '#fff',
      },
    },
  };

  dataForm!: FormGroup;

  private readonly weightRecordService = inject(WeightRecordService);
  private readonly fb = inject(FormBuilder);

  ngOnInit(): void {
    const today = new Date();
    const monthAgo = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
    this.dataForm = this.fb.group({
      chartType: this.fb.nonNullable.control<ChartPeriodType>('DAY', Validators.required),
      startDate: new FormControl(this.toIsoDateOnly(monthAgo), Validators.required),
      endDate: new FormControl(this.toIsoDateOnly(today), Validators.required),
    });
    this.updateChartData();
  }

  updateChartData(): void {
    const v = this.dataForm.getRawValue();
    const request: WeightChartRequest = {
      startDate: v.startDate,
      endDate: v.endDate,
      chartPeriodType: v.chartType,
    };

    this.weightRecordService.createChart(request).subscribe({
      next: (response) => {
        const dto = this.normalizeChartDto(response);
        this.weightChart.set(dto);
        this.applyChartDto(dto);
        this.chartHydrated.set(true);
      },
      error: () => {
        this.weightChart.set(this.emptyChartDto());
        this.chartHydrated.set(true);
      },
    });
  }

  private emptyChartDto(): WeightChartDataDTO {
    return {
      weightList: [],
      period: 'DAY',
      minWeight: 0,
      maxWeight: 0,
      avgWeight: 0,
      weightChange: 0,
      startDate: new Date(),
      endDate: new Date(),
    };
  }

  private normalizeChartDto(
    data: WeightChartDataDTO | null | undefined,
  ): WeightChartDataDTO {
    const defaults = this.emptyChartDto();
    if (!data) {
      return defaults;
    }
    return {
      ...defaults,
      ...data,
      weightList: data.weightList ?? [],
    };
  }

  private toIsoDateOnly(d: Date): string {
    return d.toISOString().slice(0, 10);
  }

  private parseDate(value: Date | string): Date {
    return value instanceof Date ? value : new Date(value);
  }

  private clearLineChart(): void {
    this.lineChartData = {
      labels: [],
      datasets: [{...this.lineChartData.datasets[0], data: []}],
    };
  }

  private applyChartDto(dto: WeightChartDataDTO | null | undefined): void {
    if (!dto) {
      return;
    }

    const points = dto.weightList ?? [];
    if (!points.length) {
      this.clearLineChart();
      return;
    }

    const sorted = [...points].sort(
      (a, b) => this.parseDate(a.date).getTime() - this.parseDate(b.date).getTime(),
    );
    const labels = sorted.map((p) => this.parseDate(p.date).toLocaleDateString('ru-RU'));
    const values = sorted.map((p) => p.weight);
    const pad = 2;
    const yMin = dto.minWeight - pad;
    const yMax = dto.maxWeight + pad;

    this.lineChartData = {
      labels,
      datasets: [{...this.lineChartData.datasets[0], data: values}],
    };

    this.lineChartOptions = {
      ...this.lineChartOptions,
      scales: {
        ...this.lineChartOptions.scales,
        y: {
          ...this.lineChartOptions.scales?.['y'],
          suggestedMin: yMin,
          suggestedMax: yMax,
        },
      },
    };
  }
}
