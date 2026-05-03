import {Component, computed, input} from '@angular/core';

@Component({
  selector: 'app-bmi-diagram',
  imports: [],
  templateUrl: './bmi-diagram.html',
  styleUrl: './bmi-diagram.css',
})
export class BmiDiagram {
  bmi = input.required<number>();

  private readonly scaleMin = 16;
  private readonly scaleMax = 40;
  protected readonly markerPercent = computed(() => {
    const v = this.bmi();
    const t = (v - this.scaleMin) / (this.scaleMax - this.scaleMin);
    return Math.min(100, Math.max(0, t * 100));
  });
}
