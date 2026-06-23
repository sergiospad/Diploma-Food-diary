import {Component, inject, input, model, OnInit, output, signal} from '@angular/core';
import RecipeShowDTO from '../../../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditProjection from '../../../../DTO/entity_dto/recipe/recipe-edit-projection';
import CookingStageShowDTO from '../../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-show.dto';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatIcon} from '@angular/material/icon';
import CookingStageEditProjection
  from '../../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-edit-projection';
import {switchMap, tap} from 'rxjs/operators';
import {ImageUploadService} from '../../../../image_services/image-upload.service';
import ImageModelService from '../../../../service/recipe_resource/image-model.service';

@Component({
  selector: 'app-edit-cooking-stage',
  imports: [
    MatButton,
    MatFormField,
    MatIcon,
    MatInput,
    MatLabel
  ],
  templateUrl: './edit-cooking-stage.html',
  styleUrl: './edit-cooking-stage.css',
})
export class EditCookingStage implements OnInit {
  private readonly imageModelService = inject(ImageModelService);
  private readonly imageUploadService = inject(ImageUploadService);
  private copyStage: CookingStageShowDTO = {} as CookingStageShowDTO;
  editIndex = output<number>();
  index = input<number>(-1);
  recipe = model<RecipeShowDTO>({} as RecipeShowDTO);
  recipeEdit = model<RecipeEditProjection>({} as RecipeEditProjection);
  cookingStageProjection = signal<CookingStageEditProjection>({} as CookingStageEditProjection);
  image = signal<string>("");
  cookingStage = signal<CookingStageShowDTO>({} as CookingStageShowDTO);

  ngOnInit(): void {
    const stage = this.recipe().cookingStages[this.index()];
    if (!stage) {
      return;
    }
    this.cookingStage.set({...stage});
    this.copyStage = this.copy();
    this.cookingStageProjection.set({
      id: stage.id,
      description: stage.description,
      imageID: stage.imageId,
    } as CookingStageEditProjection);
    this.imageModelService.getImage(stage.imageId).pipe(
      switchMap((blob) => this.imageUploadService.convertBlobToDataUrl(blob)),
      tap((url) => this.image.set(url)),
    ).subscribe();
  }

  protected selectImage(event: Event) {
    const input = event.target as HTMLInputElement
    if (input.files?.[0]) {
      this.cookingStageProjection.update((data) => ({
        ...data,
        id: data.id ?? this.cookingStage().id,
        description: data.description ?? this.cookingStage().description,
        image: input.files![0],
        imageID: this.cookingStage().imageId,
      }));
      this.imageUploadService.convertBlobToDataUrl(input.files[0])
        .then(r => this.image.set(r))
    }
  }

  protected onDescriptionChange(value: string) {
    this.cookingStageProjection.update(data=> (
      {...data,
        description: value
      }))
    this.cookingStage.update(stage => ({
      ...stage,
      description: value
    }));
  }

  protected addToRecipe() {
    const projection = this.cookingStageProjection();
    const normalizedProjection = {
      ...projection,
      id: projection.id ?? this.cookingStage().id,
      description: projection.description ?? this.cookingStage().description,
      imageID: projection.imageID ?? this.cookingStage().imageId,
    } as CookingStageEditProjection;

    this.recipeEdit.update(rec => ({
      ...rec,
      editedStages: [
        ...(rec.editedStages ?? []).filter((stage) => stage.id !== normalizedProjection.id),
        normalizedProjection,
      ]
    }));
    this.recipe.update(rec=>({
      ...rec,
      cookingStages: rec.cookingStages.map((stage, idx) =>
        idx === this.index() ? this.cookingStage() : stage,
      )
    }))
    this.reset();
  }

  copy(): CookingStageShowDTO {
    return {
      id: this.cookingStage().id,
      description: this.cookingStage().description,
      imageId: this.cookingStage().imageId,
      stageNumber: this.cookingStage().stageNumber,
    } as CookingStageShowDTO;
  }

  protected onAbort() {
    this.cookingStage.set({...this.copyStage})
    this.reset();
  }

  reset(): void {
    this.cookingStageProjection.set({} as CookingStageEditProjection);
    this.cookingStage.set({} as CookingStageShowDTO);
    this.image.set("");
    this.editIndex.emit(-1);
  }
}
