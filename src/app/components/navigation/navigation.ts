import {AsyncPipe} from '@angular/common';
import {Component, DestroyRef, inject, OnInit} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import AvatarIndexedDb from '../../image_services/avatar-indexed.db';
import {TokenStorageService} from '../../security/token-storage.service';
import UserProfileDto from '../../DTO/entity_dto/user/user-profile.dto';
import {NavigationEnd, Router, RouterLink} from '@angular/router';
import {FEED_ROOT, LOGIN} from '../../util/roots';
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
import {fromEvent, Observable, of} from 'rxjs';
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  filter,
  finalize,
  map,
  shareReplay,
  startWith,
  switchMap,
} from 'rxjs/operators';
import RecipeSummarySearchDTO from '../../DTO/entity_dto/recipe/recipe-summary-search.dto';
import {markWord} from '../../util/regex-search-editor';
import {ImageUploadService} from '../../image_services/image-upload.service';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [
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
  private static readonly AUTH_USER_STORAGE_KEY = 'auth-user';

  private readonly destroyRef = inject(DestroyRef);
  private readonly avatarRepository = inject(AvatarIndexedDb);
  private readonly tokenService = inject(TokenStorageService);
  private readonly recipeService = inject(RecipeService);
  private readonly router = inject(Router);
  private readonly imageUploadService = inject(ImageUploadService);

  protected readonly LOGIN = LOGIN;
  protected readonly FEED_ROOT = FEED_ROOT;
  protected readonly stockPic = 'https://play-lh.googleusercontent.com/QTGIa44vlItPa2hs73btKocNVJfK4qEdi8EEiF8GG9JvcGSN1cVZ-gqI_2zDgGN19A=w480-h960';
  protected readonly markWord = markWord;

  protected user: UserProfileDto|null = null;
  protected avatarUrl: Promise<string> | undefined;
  protected isSearchLoading = false;

  private searchPending = 0;
  protected readonly searchControl = new FormControl('', { nonNullable: true });

  ngOnInit(): void {
    this.syncUserFromStorage();

    this.router.events
      .pipe(
        filter((e): e is NavigationEnd => e instanceof NavigationEnd),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe(() => this.syncUserFromStorage());

    fromEvent<StorageEvent>(globalThis, 'storage')
      .pipe(
        filter(
          (e) =>
            e.storageArea === globalThis.sessionStorage &&
            e.key === NavigationComponent.AUTH_USER_STORAGE_KEY,
        ),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe(() => this.syncUserFromStorage());
  }

  private syncUserFromStorage(): void {
    this.user = this.tokenService.getUser();
    this.avatarUrl = this.user
      ? this.avatarRepository
          .getBlob()
          .then((blob) =>
            blob
              ? this.imageUploadService.convertBlobToDataUrl(blob)
              : this.stockPic,
          )
      : undefined;
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
    void this.router.navigate(['/', this.FEED_ROOT], {
      queryParams: { search: query },
    });
  }

  protected onSearchSelect(recipe: RecipeTitleSearchDTO | RecipeSummarySearchDTO): void {
    //TODO добавить переход на страницу рецепта когда будет готово
    void this.router.navigate(['/', this.FEED_ROOT], {
      queryParams: { search: recipe.name, recipeId: recipe.id },
    });
  }

  logout(): void {
    this.tokenService.logout();
    this.user = null;
    this.avatarUrl = undefined;
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

