import {Component, inject, OnInit, signal} from '@angular/core';
import CategoryShowDto from '../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import CategoryService from '../../service/recipe_resource/category.service';
import {ShowCategory} from './show-category/show-category';
import {MatButton} from '@angular/material/button';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {
  AddCoefficient,
  AddCoefficientDialogData
} from './show-category/show-coefficient/add-coefficient/add-coefficient';
import {AddCategory} from './add-category/add-category';

@Component({
  selector: 'app-categories',
  imports: [
    ShowCategory,
    MatButton
  ],
  templateUrl: './categories.html',
  styleUrl: './categories.css',
})
export class Categories implements OnInit {
  dialog = inject(MatDialog);
  categoryService = inject(CategoryService);

  categories = signal<CategoryShowDto[]>([]);
  protected loading = true;

  ngOnInit(): void {
    this.categoryService.getAllShow().subscribe({
      next: (show) => {
        this.categories.set( show);
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  protected addCategory() {
    const cfg = new MatDialogConfig();
    cfg.width = '900px';
    this.dialog
      .open(AddCategory, cfg)
      .afterClosed()
      .subscribe((updated:CategoryShowDto) => {
        if (updated) {
          this.categories.update(cat=>[...cat, updated]);
        }
      });
  }
}
