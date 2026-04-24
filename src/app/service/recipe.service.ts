import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Endpoint} from '../util/endpoint';
import RecipePreviewRequest from '../DTO/requests/recipe-preview.request';
import {Observable} from 'rxjs';
import RecipePreviewDTO from '../DTO/recipe/recipe-preview.dto';
import RecipeSummarySearchDTO from '../DTO/recipe/recipe-summary-search.dto';
import RecipeTitleSearchDTO from '../DTO/recipe/recipe-title-search.dto';
import RecipeEditDto from '../DTO/recipe/recipe-edit.dto';
import RecipeShowDTO from '../DTO/recipe/recipe-show.dto';
import RecipeCreateDTO from '../DTO/recipe/recipe-create.dto';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
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
