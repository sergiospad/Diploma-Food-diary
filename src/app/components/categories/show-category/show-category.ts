import {Component, model} from '@angular/core';
import CategoryShowDto from '../../../DTO/entity_dto/recipe-recource/category/category-show.dto';

@Component({
  selector: 'app-show-category',
  imports: [],
  templateUrl: './show-category.html',
  styleUrl: './show-category.css',
})
export class ShowCategory {
  category = model.required<CategoryShowDto>();

}
