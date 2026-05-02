import {Component, inject, OnInit} from '@angular/core';
import CategoryShowDto from '../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import CategoryService from '../../service/recipe_resource/category.service';
import {ShowCategory} from './show-category/show-category';

@Component({
  selector: 'app-categories',
  imports: [
    ShowCategory
  ],
  templateUrl: './categories.html',
  styleUrl: './categories.css',
})
export class Categories implements OnInit {
  categoryService = inject(CategoryService);

  categories: CategoryShowDto[] = [];

  ngOnInit(): void {
    this.categoryService.getAllShow()
      .subscribe(show => this.categories = show);
  }


}
