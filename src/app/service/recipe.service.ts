import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../util/endpoint';
import RecipePreviewRequest from '../DTO/requests/recipe-preview.request';
import {Observable} from 'rxjs';
import RecipePreviewDTO from '../DTO/entity_dto/recipe/recipe-preview.dto';
import RecipeSummarySearchDTO from '../DTO/entity_dto/recipe/recipe-summary-search.dto';
import RecipeTitleSearchDTO from '../DTO/entity_dto/recipe/recipe-title-search.dto';
import RecipeCreateDTO from '../DTO/entity_dto/recipe/recipe-create.dto';
import RecipeShowDTO from '../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditDto from '../DTO/entity_dto/recipe/recipe-edit.dto';


@Injectable({
  providedIn: 'root',
})
export default class RecipeService {
  private readonly http = inject(HttpClient);
  private readonly postAPI = new Endpoint('recipe');

  getAllRecipePreviews(recipe: RecipePreviewRequest, page:number):Observable<RecipePreviewDTO[]>{
     return this.http.post<RecipePreviewDTO[]>(
       this.postAPI.builder()
         .points("previews")
         .addParam("page", page.toString())
         .build(),
       recipe);
  }

  summarySearch(searchItem:string):Observable<RecipeSummarySearchDTO[]>{
    return this.http.get<RecipeSummarySearchDTO[]>(
      this.postAPI.builder()
        .points("search", "summary")
        .addParam("searchItem", searchItem)
        .build())
  }

  titleSearch(searchItem: string): Observable<RecipeTitleSearchDTO[]> {
    return this.http.get<RecipeTitleSearchDTO[]>(
      this.postAPI.builder()
        .points("search", "title")
        .addParam("titleSearch", searchItem)
        .build());
  }

  createRecipe(recipe: RecipeCreateDTO):Observable<RecipeShowDTO>{
    return this.http.put<RecipeShowDTO>(
      this.postAPI.builder()
        .points("create")
        .build()
      , recipe)
  }

  updateRecipe(recipe: RecipeEditDto):Observable<RecipeShowDTO>{
    return this.http.patch<RecipeShowDTO>(
      this.postAPI.builder()
        .points("edit")
        .build(),
      recipe)
  }

  showRecipe(id: number): Observable<RecipeShowDTO>{
    return this.http.get<RecipeShowDTO>(
      this.postAPI.builder()
        .points("show")
        .addParam("id", id.toString())
        .build())
  }

}
