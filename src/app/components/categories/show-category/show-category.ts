import {Component, inject, model} from '@angular/core';
import CategoryShowDto from '../../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import {ShowCoefficient} from './show-coefficient/show-coefficient';
import {MatButton} from '@angular/material/button';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {
  AddCoefficient,
  AddCoefficientDialogData,
} from './show-coefficient/add-coefficient/add-coefficient';

@Component({
  selector: 'app-show-category',
  imports: [
    ShowCoefficient,
    MatButton
  ],
  templateUrl: './show-category.html',
  styleUrl: './show-category.css',
})
export class ShowCategory {
  category = model.required<CategoryShowDto>();
  dialog = inject(MatDialog);

  protected addCoefficient() {
    const cfg = new MatDialogConfig<AddCoefficientDialogData>();
    cfg.width = '900px';
    cfg.data = {categoryId: this.category().id};
    this.dialog
      .open(AddCoefficient, cfg)
      .afterClosed()
      .subscribe((updated) => {
        if (updated) {
          this.category.set(updated);
        }
      });
  }
}
