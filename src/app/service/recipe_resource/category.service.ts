import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../../util/endpoint';
import {Observable} from 'rxjs';
import CategoryNameDTO from '../../DTO/entity_dto/recipe-recource/category/category-name.dto';
import CategoryCreateDTO from '../../DTO/entity_dto/recipe-recource/category/category-create.dto';
import CategoryShowDTO from '../../DTO/entity_dto/recipe-recource/category/category-show.dto';
import CategoryAddCoefficientDTO from '../../DTO/entity_dto/recipe-recource/category/category-add-coefficient.dto';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private readonly http = inject(HttpClient);
  private readonly categoryAPI = new Endpoint('category');

  getAll():Observable<CategoryNameDTO[]>{
    return this.http.get<CategoryNameDTO[]>(
      this.categoryAPI.builder()
        .points("all", "name")
        .build())
  }

  createCategory(category: CategoryCreateDTO):Observable<CategoryShowDTO>{
    return this.http.put<CategoryShowDTO>(
      this.categoryAPI.builder()
        .points("create")
        .build(),
      category)
  }

  getAllShow():Observable<CategoryShowDTO[]>{
    return this.http.get<CategoryShowDTO[]>(
      this.categoryAPI.builder()
        .points("all", "show")
        .build()
    )
  }

  editCategory(category:CategoryNameDTO):Observable<any>{
    return this.http.patch(
      this.categoryAPI.builder()
        .points("edit")
        .build(),
      category)
  }

  addCoefficient(coefficients:CategoryAddCoefficientDTO):Observable<CategoryShowDTO>{
    return this.http.patch<CategoryShowDTO>(
      this.categoryAPI.builder()
        .points("add", "coefficient")
        .build()
      ,coefficients)
  }
}

