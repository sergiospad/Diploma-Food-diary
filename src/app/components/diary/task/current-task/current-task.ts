import {DecimalPipe} from '@angular/common';
import {Component, computed, inject, model} from '@angular/core';
import {MatProgressBar} from '@angular/material/progress-bar';
import CurrentTaskShowDTO from '../../../../DTO/entity_dto/diary/task/current-task-show.dto';
import {targetLabels} from '../task-archives/task-archives';
import ChangeStatusTaskRequest from '../../../../DTO/requests/change-status-task.request';
import TaskService from '../../../../service/diary/task.service';
import {NotificationService} from '../../../../security/notification-service';

@Component({
  selector: 'app-current-task',
  imports: [MatProgressBar, DecimalPipe],
  templateUrl: './current-task.html',
  styleUrl: './current-task.css',
})
export class CurrentTask {
  readonly task = model.required<CurrentTaskShowDTO|null>();
  private readonly taskService = inject(TaskService);
  private readonly notificationService = inject(NotificationService);

  protected readonly targetLabels = targetLabels;

  protected readonly showDeterminateWeightBar = computed(() => {
    const t = this.task();
    if (!t) {
      return false;
    }
    return (
      t.startWeight != null &&
      t.currentWeight != null &&
      t.targetWeight != null &&
      Number.isFinite(t.startWeight) &&
      Number.isFinite(t.currentWeight) &&
      Number.isFinite(t.targetWeight)
    );
  });


  protected readonly weightProgressPercent = computed(() => {
    if (!this.showDeterminateWeightBar()) {
      return 0;
    }
    const task = this.task()!;
    const start = task.startWeight!;
    const cur = task.currentWeight!;
    const tgt = task.targetWeight!;

    const clamp = (x: number) => Math.min(100, Math.max(0, x));

    if (task.taskTarget === 'W_LOSS') {
      const span = start - tgt;
      if (span <= 0) {
        return 0;
      }
      return clamp(((start - cur) / span) * 100);
    }

    if (task.taskTarget === 'W_GAIN') {
      const span = tgt - start;
      if (span <= 0) {
        return 0;
      }
      return clamp(((cur - start) / span) * 100);
    }

    const bandKg = 2;
    const dist = Math.abs(cur - tgt);
    return clamp(100 - (dist / bandKg) * 100);
  });

  protected onCancel(): void {
    const cs = {
      id: this.task()!.id,
      status: 'ABORTED' as const,
      endingDate: new Date(),
    } as ChangeStatusTaskRequest;
    this.taskService.changeStatus(cs).subscribe({
      next: () => {
        this.task.set(null);
        this.notificationService.showSnackBar('Задание отменено');
      },
    });
  }
}
