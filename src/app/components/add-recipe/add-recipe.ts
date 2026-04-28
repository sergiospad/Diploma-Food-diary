import {Component, inject, OnInit} from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/input';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import IngredientCreateView from '../../DTO/entity_dto/recipe-recource/ingredient/ingredient-create.view';
import {MatDialog} from '@angular/material/dialog';
import CookingStageCreateView from '../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.view';
import {AddCookingStage} from './add-cooking-stage/add-cooking-stage';
import TagDto from '../../DTO/entity_dto/recipe-recource/tag.dto';
import TagService from '../../service/recipe_resource/tag.service';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import RecipeCreateDTO from '../../DTO/entity_dto/recipe/recipe-create.dto';
import CookingStageCreateDTO from '../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.dto';
import ImageModelService from '../../service/recipe_resource/image-model.service';
import RecipeService from '../../service/recipe.service';
import {forkJoin, Observable, of} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {RecipeMainInfoSectionComponent} from './recipe-main-info-section/recipe-main-info-section';
import {
  RecipeMainInfoIngredientsSection
} from './recipe-main-info-ingredients-section/recipe-main-info-ingredients-section';

@Component({
  selector: 'app-add-recipe',
  imports: [
    MatFormField,
    MatLabel,
    MatButton,
    MatIconButton,
    MatIcon,
    AddCookingStage,
    MatOption,
    MatSelect,
    ReactiveFormsModule,
    FormsModule,
    RecipeMainInfoSectionComponent,
    RecipeMainInfoIngredientsSection,
  ],
  templateUrl: './add-recipe.html',
  styleUrl: './add-recipe.css',
})
class AddRecipeComponent implements OnInit {

  private readonly tagService = inject(TagService);
  private readonly imageService = inject(ImageModelService);
  private readonly recipeService = inject(RecipeService);
  private readonly router = inject(Router);

  protected title?: string;
  protected summary?: string;
  protected titleImageBlob?: Blob;
  protected isPrivate?: boolean;
  protected cookingTime?:number;
  protected ingredients: IngredientCreateView[] = [];
  protected cookingStages?: CookingStageCreateView[];
  protected allTags?: TagDto[];
  protected selectedTags: TagDto[] = [];

  protected readonly maxCookingStages = 30;


  ngOnInit(): void {
    this.cookingStages = [];
    this.tagService.getAllTags()
      .subscribe(data=>this.allTags = data);
  }

  protected addCookingStageToList($event: CookingStageCreateView) {
    this.cookingStages ??= [];
    this.cookingStages.push($event);
  }

  protected submitRecipe(): Observable<RecipeCreateDTO> {
    const stages = this.cookingStages ?? [];

    return this.imageService.uploadImage(this.titleImageBlob!, 'RECIPE').pipe(
      switchMap((illustrationID) => {
        return forkJoin(
          stages.map((s) =>
            s.imageBlob == null
              ? of(-1)
              : this.imageService.uploadImage(s.imageBlob, 'COOKING_STAGE'),
          ),
        ).pipe(
          map((imageIds) => {
            const stageDtos: CookingStageCreateDTO[] = stages.map((s, i) => ({
              description: s.description,
              stageNumber: i + 1,
              imageID: imageIds[i]===-1? undefined : imageIds[i],
            }));
            return this.buildRecipeCreateDto(illustrationID, stageDtos);
          }),
        );
      }),
    );
  }

  protected saveRecipe(): void {
    this.submitRecipe()
      .pipe(switchMap((dto) => this.recipeService.createRecipe(dto)))
      .subscribe({
        next: ()=> {
          this.router.navigate(['/'])
          .then(()=>globalThis.location.reload())
          },
        error: (err) => console.error(err),
      });
  }

  private buildRecipeCreateDto(
    illustrationID: number,
    stages: CookingStageCreateDTO[],
  ): RecipeCreateDTO {
    return {
      name: this.title!,
      summary: this.summary!,
      illustrationID,
      isPrivate: this.isPrivate?? true,
      tags: this.selectedTags.map((t) => t.id),
      cookingTime:  this.cookingTime,
      ingredients: (this.ingredients ?? []).map((ing) => ({
        productID: ing.productID,
        amount: ing.amount,
        measureUnitID: ing.measureUnitID,
      })),
      stages,
    };
  }

  /**
   * `true`, если форму можно отправить (кнопка: `[disabled]="!canSubmit()"`).
   */
  protected canSubmit(): boolean {
    if (!this.title?.trim()) return false;

    if (!this.summary?.trim()) return false;

    if (this.titleImageBlob == null) return false;

    const ingredients = this.ingredients ?? [];
    if (ingredients.length === 0) return false;

    const stages = this.cookingStages ?? [];
    return !(stages.length === 0 || stages.length > this.maxCookingStages);

  }


  protected removeStage(index: number) {
    this.cookingStages ??= [];
    this.cookingStages.splice(index, 1);
  }
}

export default AddRecipeComponent
