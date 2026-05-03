import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {TaskTarget} from '../../../../DTO/types';
import TaskService from '../../../../service/diary/task.service';
import TaskCreateDTO from '../../../../DTO/entity_dto/diary/task/task-create.dto';
import {MatDialogRef} from '@angular/material/dialog';
import CategoryShowDto from '../../../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';

@Component({
  selector: 'app-add-task',
  imports: [
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
  ],
  templateUrl: './add-task.html',
  styleUrl: './add-task.css',
})
export class AddTask implements OnInit {
  taskService = inject(TaskService);
  private readonly dialogRef = inject(MatDialogRef<AddTask>);
  taskForm!: FormGroup;
  fb= inject(FormBuilder);
  targetMap: ReadonlyArray<{value: TaskTarget; label: string}> = [
    {value: 'W_LOSS', label: 'Похудение'},
    {value: 'W_GAIN', label: 'Набор веса'},
    {value: 'W_KEEP', label: 'Сохранение веса'},
  ];

  ngOnInit(): void {
    this.taskForm = this.fb.group({
      taskTarget: [null, [Validators.required]],
      caloriesDeficit: [null, [Validators.required]],
      targetWeight: [null, [Validators.required]],
      beginningDate: [new Date(), [Validators.required]]
    })
  }

  onClick(){
    const createTask = {
      target: this.taskForm.controls['taskTarget'].value,
      targetWeight: Number(this.taskForm.controls['targetWeight'].value),
      beginningDate: this.taskForm.controls['beginningDate'].value,
      caloriesDeficit: Number(this.taskForm.controls['caloriesDeficit'].value),
    }as TaskCreateDTO;
    this.taskService.createTask(createTask).subscribe(
      data=> this.dialogRef.close(data),
    )
  }




}
