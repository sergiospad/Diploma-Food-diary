import {DatePipe, DecimalPipe} from '@angular/common';
import {Component, inject, OnInit, signal} from '@angular/core';
import TaskArchiveShowDTO from '../../../../DTO/entity_dto/diary/task/task-archive-show.dto';
import {TaskStatus, TaskTarget} from '../../../../DTO/types';
import TaskService from '../../../../service/diary/task.service';

@Component({
  selector: 'app-task-archives',
  imports: [DatePipe, DecimalPipe],
  templateUrl: './task-archives.html',
  styleUrl: './task-archives.css',
})
export class TaskArchives implements OnInit {
  protected readonly taskArchives = signal<TaskArchiveShowDTO[]>([]);

  private readonly taskService = inject(TaskService);

  private readonly targetLabels: Record<TaskTarget, string> = {
    W_LOSS: 'Похудение',
    W_GAIN: 'Набор веса',
    W_KEEP: 'Поддержание веса',
  };

  private readonly statusLabels: Record<TaskStatus, string> = {
    PLANNED: 'Запланирована',
    ONGOING: 'В процессе',
    COMPLETED: 'Завершена',
    ABORTED: 'Остановлена',
  };

  ngOnInit(): void {
    this.taskService.getArchiveTasks().subscribe((data) => this.taskArchives.set(data));
  }

  protected targetLabel(target: TaskTarget): string {
    return this.targetLabels[target] ?? target;
  }

  protected statusLabel(status: TaskStatus): string {
    return this.statusLabels[status] ?? status;
  }
}
