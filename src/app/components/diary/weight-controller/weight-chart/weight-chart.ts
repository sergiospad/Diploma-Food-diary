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
  private readonly weightRecordService = inject(WeightRecordService);
  protected readonly weightChart = signal<WeightChartDataDTO>({
    weightList: [],
    period: 'DAY',
    minWeight: 0,
    maxWeight: 0,
    avgWeight: 0,
    weightChange: 0,
    startDate: new Date(),
    endDate: new Date(),
  });
  /** После первого ответа API показываем пустое состояние вместо «тишины». */
  protected readonly chartHydrated = signal(false);

  dataForm!: FormGroup;
  private readonly fb = inject(FormBuilder);

  /** Нативный select без overlay — список не «уезжает» к краю страницы. */
  protected readonly chartPeriodOptions: ReadonlyArray<{value: ChartPeriodType; label: string}> = [
    {value: 'DAY', label: 'День'},
    {value: 'WEEK', label: 'Неделя'},
    {value: 'MONTH', label: 'Месяц'},
    {value: 'YEAR', label: 'Год'},
  ];

  ngOnInit(): void {
    const endDate = new Date();
    const startDate = new Date(endDate.getFullYear(), endDate.getMonth() - 1, endDate.getDate());
    this.dataForm = this.fb.group({
      chartType: ['DAY' as ChartPeriodType, Validators.required],
      startDate: new FormControl(
        startDate.toISOString().slice(0, 10),
        Validators.required,
      ),
      endDate: new FormControl(endDate.toISOString().slice(0, 10), Validators.required),
    });
    this.updateChartData();
  }

  updateChartData(): void {
    const raw = this.dataForm.getRawValue();
    const req = {
      startDate: raw.startDate,
      endDate: raw.endDate,
      chartPeriodType: raw.chartType,
    } as WeightChartRequest;
    this.weightRecordService.createChart(req).subscribe({
      next: (data) => {
        this.weightChart.set(data);
        this.applyChartDto(data);
        this.chartHydrated.set(true);
      },
      error: () => this.chartHydrated.set(true),
    });
  }

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

  private parseDate(d: Date | string): Date {
    return d instanceof Date ? d : new Date(d);
  }

  private applyChartDto(dto: WeightChartDataDTO): void {
    const list = dto.weightList ?? [];
    if (!list.length) {
      this.lineChartData = {
        labels: [],
        datasets: [{...this.lineChartData.datasets[0], data: []}],
      };
      return;
    }

    const sorted = [...list].sort(
      (a, b) => this.parseDate(a.date).getTime() - this.parseDate(b.date).getTime(),
    );
    const labels = sorted.map((p) =>
      this.parseDate(p.date).toLocaleDateString('ru-RU'),
    );
    const values = sorted.map((p) => p.weight);
    const pad = 2;
    const yMin = dto.minWeight - pad;
    const yMax = dto.maxWeight + pad;

    this.lineChartData = {
      labels,
      datasets: [
        {
          ...this.lineChartData.datasets[0],
          data: values,
        },
      ],
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
