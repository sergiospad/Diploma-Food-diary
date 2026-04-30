import {Component, inject, input, model, OnInit, output, signal} from '@angular/core';
import CookingStageShowDTO from '../../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-show.dto';
import CookingStageEditProjection
  from '../../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-edit-projection';
import RecipeShowDTO from '../../../../DTO/entity_dto/recipe/recipe-show.dto';
import {ImageUploadService} from '../../../../image_services/image-upload.service';
import ImageModelService from '../../../../service/recipe_resource/image-model.service';
import {map, switchMap, tap} from 'rxjs/operators';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import RecipeEditProjection from '../../../../DTO/entity_dto/recipe/recipe-edit-projection';

@Component({
  selector: 'app-edit-recipe-show-stage',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton
  ],
  templateUrl: './edit-recipe-show-stage.html',
  styleUrl: './edit-recipe-show-stage.css',
})
export class EditRecipeShowStage implements OnInit {
  private readonly imageUpload = inject(ImageUploadService);
  private readonly imageModelService = inject(ImageModelService);
  protected readonly editIndex = output<number>();
  index = input<number>(-1);
  recipe = model<RecipeShowDTO>({}as RecipeShowDTO);
  recipeEdit = model<RecipeEditProjection>({} as RecipeEditProjection);
  image = signal<string>("");
  cookingStage = signal<CookingStageShowDTO>({}as CookingStageShowDTO);

  ngOnInit(): void {
    const stage = this.recipe().cookingStages[this.index()];
    if (!stage) {
      return;
    }
    this.cookingStage.set(stage);
    this.imageModelService.getImage(stage.imageId).pipe(
      switchMap((blob) => this.imageUpload.convertBlobToDataUrl(blob)),
      tap((url) => {
        queueMicrotask(() => this.image.set(url));
      }),
    ).subscribe();
  }

  protected removeStage() {
    this.recipe.update((rec) => ({
      ...rec,
      cookingStages: rec.cookingStages.filter((_, i) => i !== this.index()),
    }));
    this.recipeEdit.update((rec) => ({
      ...rec,
      removedStages: [...(rec.removedStages ?? []), this.cookingStage().id],
    }));
  }

  protected editStage() {
    this.editIndex.emit(this.index());
  }
}
