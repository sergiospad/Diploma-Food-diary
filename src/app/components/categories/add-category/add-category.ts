import {Component, inject, signal} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatDialogRef} from '@angular/material/dialog';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import CategoryCreateDTO from '../../../DTO/entity_dto/recipe-recource/category/category-create.dto';
import CategoryShowDto from '../../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import {NotificationService} from '../../../security/notification-service';
import CategoryService from '../../../service/recipe_resource/category.service';

@Component({
  selector: 'app-add-category',
  imports: [MatButton, MatFormField, MatInput, MatLabel],
  templateUrl: './add-category.html',
  styleUrl: './add-category.css',
})
export class AddCategory {
  private readonly dialogRef = inject(MatDialogRef<AddCategory, CategoryShowDto | undefined>);
  private readonly categoryService = inject(CategoryService);
  private readonly notificationService = inject(NotificationService);

  protected readonly name = signal('');

  protected cancel(): void {
    this.dialogRef.close();
  }

  protected submitCategory(): void {
    const trimmed = this.name().trim();
    if (!trimmed) {
      return;
    }
    const cat = {name: trimmed, coefficients: []} as CategoryCreateDTO;
    this.categoryService.createCategory(cat).subscribe({
      next: (data) => this.dialogRef.close(data),
      error: () => this.notificationService.showSnackBar('Не удалось создать категорию'),
    });
  }

  protected onInputName(event: Event): void {
    const target = event.target as HTMLInputElement | null;
    if (target) {
      this.name.set(target.value);
    }
  }
}
