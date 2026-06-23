import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatDialogRef} from '@angular/material/dialog';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import TaskCreateDTO from '../../../../DTO/entity_dto/diary/task/task-create.dto';
import {TaskTarget} from '../../../../DTO/types';
import TaskService from '../../../../service/diary/task.service';

@Component({
  selector: 'app-add-task',
  imports: [ReactiveFormsModule, MatFormField, MatInput, MatLabel, MatButton],
  templateUrl: './add-task.html',
  styleUrl: './add-task.css',
})
export class AddTask implements OnInit {
  private readonly taskService = inject(TaskService);
  private readonly dialogRef = inject(MatDialogRef<AddTask>);
  private readonly fb = inject(FormBuilder);

  taskForm!: FormGroup;

  readonly targetMap: ReadonlyArray<{value: TaskTarget; label: string}> = [
    {value: 'W_LOSS', label: 'Похудение'},
    {value: 'W_GAIN', label: 'Набор веса'},
    {value: 'W_KEEP', label: 'Сохранение веса'},
  ];

  ngOnInit(): void {
    const today = new Date().toISOString().slice(0, 10);
    this.taskForm = this.fb.group({
      taskTarget: this.fb.nonNullable.control<TaskTarget>('W_LOSS', Validators.required),
      targetWeight: new FormControl<number | null>(null, Validators.required),
      beginningDate: new FormControl(today, Validators.required),
      caloriesDeficit: new FormControl<number | null>(null, Validators.required),
    });
  }

  protected onSubmit(): void {
    if (this.taskForm.invalid) {
      return;
    }
    const v = this.taskForm.getRawValue();
    const createTask = {
      target: v.taskTarget,
      targetWeight: Number(v.targetWeight),
      beginningDate: v.beginningDate,
      caloriesDeficit: Number(v.caloriesDeficit),
    } as TaskCreateDTO;
    this.taskService.createTask(createTask).subscribe((data) => this.dialogRef.close(data));
  }
}
