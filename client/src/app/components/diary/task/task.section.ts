import {Component, inject, OnInit, signal} from '@angular/core';
import TaskService from '../../../service/diary/task.service';
import CurrentTaskShowDTO from '../../../DTO/entity_dto/diary/task/current-task-show.dto';
import {MatButton} from '@angular/material/button';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {AddTask} from './add-task/add-task';
import {TaskArchives} from './task-archives/task-archives';
import {CurrentTask} from './current-task/current-task';

@Component({
  selector: 'app-task',
  imports: [CurrentTask, MatButton],
  templateUrl: './task.section.html',
  styleUrl: './task.section.css',
})
export class TaskSection implements OnInit {
  taskService = inject(TaskService);
  dialog = inject(MatDialog);

  currentTask= signal<CurrentTaskShowDTO|null>(null);


  ngOnInit(): void {
    this.taskService.getCurrentTask().subscribe((cur) => this.currentTask.set(cur));
  }

  onClick(){
    const cfg = new MatDialogConfig<AddTask>();
    cfg.width = '900px';
    this.dialog
      .open(AddTask, cfg)
      .afterClosed()
      .subscribe((updated:CurrentTaskShowDTO) => {
        if (updated) {
          this.currentTask.set(updated);
        }
      });
  }

  protected onClickArchive() {
    const cfg = new MatDialogConfig<TaskArchives>();
    cfg.width = '900px';
    this.dialog.open(TaskArchives, cfg);
  }
}
