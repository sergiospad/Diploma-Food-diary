import {AsyncPipe} from '@angular/common';
import {Component, inject, OnInit} from '@angular/core';
import AvatarIndexedDb from '../../image_services/avatar-indexed.db';
import {TokenStorageService} from '../../security/token-storage.service';
import UserProfileDto from '../../DTO/entity_dto/user/user-profile.dto';
import {Router, RouterLink} from '@angular/router';
import {FEED_ROOT, LOGIN} from '../../util/roots';
import {MatToolbar} from '@angular/material/toolbar';
import {MatTooltip} from '@angular/material/tooltip';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {MatDivider} from '@angular/material/list';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {MatFormField} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import RecipeService from '../../service/recipe.service';
import RecipeTitleSearchDTO from '../../DTO/entity_dto/recipe/recipe-title-search.dto';
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from '@angular/material/autocomplete';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, finalize, map, shareReplay, startWith, switchMap} from 'rxjs/operators';
import RecipeSummarySearchDTO from '../../DTO/entity_dto/recipe/recipe-summary-search.dto';
import {markWord} from '../../util/regex-search-editor';

@Component({
  selector: 'app-navigation',
  imports: [
    MatToolbar,
    RouterLink,
    MatTooltip,
    MatIconButton,
    MatIcon,
    MatMenuTrigger,
    AsyncPipe,
    MatDivider,
    MatMenuItem,
    MatMenu,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatAutocomplete,
    MatAutocompleteTrigger,
    MatOption,
  ],
  templateUrl: './navigation.html',
  styleUrl: './navigation.css',
})
export class NavigationComponent implements OnInit {
  private readonly avatarRepository = inject(AvatarIndexedDb);
  private readonly tokenService = inject(TokenStorageService);
  private readonly recipeService = inject(RecipeService);
  private readonly router = inject(Router);

  protected readonly LOGIN = LOGIN;
  protected readonly FEED_ROOT = FEED_ROOT;
  protected readonly stockPic = 'https://play-lh.googleusercontent.com/QTGIa44vlItPa2hs73btKocNVJfK4qEdi8EEiF8GG9JvcGSN1cVZ-gqI_2zDgGN19A=w480-h960';
  protected readonly markWord = markWord;

  protected user: UserProfileDto|null = null;
  protected avatar:Promise<Blob> | undefined;
  protected isSearchLoading = false;

  private searchPending = 0;
  protected readonly searchControl = new FormControl('', { nonNullable: true });

  ngOnInit(): void {
    this.user = this.tokenService.getUser();
    if (this.user) {
      this.avatar = this.avatarRepository.getBlob();
    }
  }


  protected readonly searchTitleResults$: Observable<RecipeTitleSearchDTO[]> =
    this.searchControl.valueChanges.pipe(
      startWith(this.searchControl.value),
      map((value) => value.trim()),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((query) =>
        query.length < 2
          ? of([])
          : this.withSearchLoading(this.recipeService.titleSearch(query)).pipe(
              catchError(() => of([])),
            ),
      ),
      shareReplay({bufferSize: 1, refCount: true}),
    );

  protected readonly searchSummaryResults$: Observable<RecipeSummarySearchDTO[]> =
    this.searchControl.valueChanges.pipe(
      startWith(this.searchControl.value),
      map((value) => value.trim()),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((query) =>
        query.length < 2
          ? of([])
          : this.withSearchLoading(this.recipeService.summarySearch(query)).pipe(
            catchError(() => of([])),
          ),
      ),
      shareReplay({bufferSize: 1, refCount: true}),
    );

  protected onSearch(): void {
    const query = this.searchControl.value.trim();
    if (!query) {
      return;
    }
    void this.router.navigate([this.FEED_ROOT], {queryParams: {search: query}});
  }

  protected onSearchSelect(recipe: RecipeTitleSearchDTO | RecipeSummarySearchDTO): void {
    //TODO добавить переход на страницу рецепта когда будет готово
    void this.router.navigate([this.FEED_ROOT], {
      queryParams: {search: recipe.name, recipeId: recipe.id},
    });
  }

  logout(){
    this.tokenService.logout();
    this.user = null;
  }

  private withSearchLoading<T>(source: Observable<T>): Observable<T> {
    this.searchPending += 1;
    this.isSearchLoading = true;
    return source.pipe(
      finalize(() => {
        this.searchPending -= 1;
        this.isSearchLoading = this.searchPending > 0;
      }),
    );
  }
}

