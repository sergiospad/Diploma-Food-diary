import {Component, computed, inject, OnInit, signal} from '@angular/core';
import {TagField} from './tag-field/tag-field';
import {ActivatedRoute, Router} from '@angular/router';
import TagDto from '../../DTO/entity_dto/recipe-recource/tag.dto';
import TagService from '../../service/recipe_resource/tag.service';
import {MatOption} from '@angular/material/core';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {SortTypeRecipe} from '../../DTO/types';
import {MatSelect} from '@angular/material/select';
import {MatFormField, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {FormsModule} from '@angular/forms';
import RecipePreviewDTO from '../../DTO/entity_dto/recipe/recipe-preview.dto';
import RecipePreviewRequest from '../../DTO/requests/recipe-preview.request';
import RecipeService from '../../service/recipe.service';
import ImageModelService from '../../service/recipe_resource/image-model.service';
import {ImageUploadService} from '../../image_services/image-upload.service';
import {TokenStorageService} from '../../security/token-storage.service';
import {catchError, map, switchMap, tap} from 'rxjs/operators';
import {forkJoin, from, of} from 'rxjs';
import {RECIPE} from '../../util/roots';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [
    TagField,
    MatOption,
    MatIcon,
    MatIconButton,
    MatSelect,
    MatFormField,
    MatLabel,
    MatButton,
    FormsModule,
  ],
  templateUrl: './feed.html',
  styleUrl: './feed.css',
})
export class FeedComponent implements OnInit {
  router = inject(Router);
  activeRoute = inject(ActivatedRoute);
  tagService = inject(TagService);
  recipeService = inject(RecipeService);
  imageService = inject(ImageModelService);
  imageUpload = inject(ImageUploadService);
  private readonly tokenStorage = inject(TokenStorageService);

  private readonly paramMap = this.activeRoute.snapshot.paramMap;

  needsFavourite = signal(false);
  tags = signal<TagDto[]>([]);
  favouriteRecipes = signal<number[]>([]);
  allTags : TagDto[]=[];
  private authorName?:string;
  sortType = signal<SortTypeRecipe>("NEWER");
  sortTypeDict =  new Map<SortTypeRecipe, string>([
    ['NEWER', 'Сначала новые'],
    ['OLDER', 'Сначала старые'],
    ['POPULAR', 'Популярные']
  ]);
  recipes = signal<RecipePreviewDTO[]>([]);

  page = signal<number>(0);

  protected readonly previewPageSize = 12;

  protected readonly displayPageNumber = computed(() => this.page() + 1);

  protected readonly canGoPrevPage = computed(() => this.page() > 0);

  protected readonly canGoNextPage = computed(
    () => this.recipes().length === this.previewPageSize,
  );

  ngOnInit(): void {
    this.tagService.getAllTags()
      .subscribe(data=>this.allTags = data);
    if(this.paramMap.has("isFavourite"))
      this.needsFavourite.set(this.paramMap.get("isFavourite")==='true')
    if(this.paramMap.has('tags')){
      this.tags.set(this.paramMap.get("tags")!
        .split(",")
        .map(Number)
        .map(tagId=> this.allTags
          .find(tag=>tag.id===tagId)!));
      if(this.paramMap.has("authorName"))
        this.authorName = this.paramMap.get("authorName")!
      if(this.paramMap.has("sortType"))
        this.sortType.set(this.paramMap.get("sortType") as SortTypeRecipe);
    }
    this.submitFilters();
  }

  /** Новые фильтры — с первой страницы (индекс 0 на сервере). */
  protected applyFilters(): void {
    this.page.set(0);
    this.submitFilters();
  }

  protected goToPrevPage(): void {
    if (!this.canGoPrevPage()) {
      return;
    }
    this.page.update((p) => p - 1);
    this.submitFilters();
  }

  protected goToNextPage(): void {
    if (!this.canGoNextPage()) {
      return;
    }
    this.page.update((p) => p + 1);
    this.submitFilters();
  }

  protected removeIngredient(id: number): void {
    this.tags.update((list) => list.filter((t) => t.id !== id));
  }

  formRequest():RecipePreviewRequest{
    return {
      isFavoriteOnly: this.needsFavourite(),
      tags: this.tags().map(tag=>tag.id),
      authorName: this.authorName,
      sortType: this.sortType(),
    }as RecipePreviewRequest;
  }
  protected submitFilters(): void {
    const request = this.formRequest();
    console.log(request);
    this.recipeService
      .getAllRecipePreviews(request, this.page(), this.previewPageSize)
      .pipe(
        switchMap((response) =>
          response.length === 0
            ? of([] as RecipePreviewDTO[])
            : forkJoin(
              response.map((res) =>
                this.imageService.getImage(res.imageID).pipe(
                  switchMap((blob) =>
                    from(this.imageUpload.convertBlobToDataUrl(blob)),
                  ),
                  map((url): RecipePreviewDTO => ({...res, image: url})),
                  catchError(() =>
                    of({...res, image: ''} as RecipePreviewDTO),
                  ),
                ),
              ),
            ),
        ),
        tap((data) => this.recipes.set(data)),
        switchMap((data) => {
          const ids = data.map((res) => res.id);
          const token = this.tokenStorage.getToken()?.trim();
          if (!token || ids.length === 0) {
            this.favouriteRecipes.set([]);
            return of(undefined);
          }
          return this.recipeService.getFavourites(ids).pipe(
            tap((fav) => this.favouriteRecipes.set(fav.recipes ?? [])),
            catchError(() => {
              this.favouriteRecipes.set([]);
              return of(undefined);
            }),
          );
        }),
      )
      .subscribe();
  }

  protected addToTags($event: TagDto) {
    this.tags.update((list) =>([...list, $event]));
  }

  isFavourite(id: number): boolean {
    return this.favouriteRecipes().includes(id);

  }

  protected removeFavourite(id: number): void {
    this.favouriteRecipes.update((list) =>
      list.filter((num) => num !== id));
    this.recipeService.toggleFavourite(id).subscribe();
  }

  protected addFavourite(id: number): void {
    this.favouriteRecipes.update((list) => [...list, id]);
    this.recipeService.toggleFavourite(id).subscribe();
  }

  protected onSelectRecipe(id: number): void {
    void this.router.navigate(['/', RECIPE, id]);
  }
}
