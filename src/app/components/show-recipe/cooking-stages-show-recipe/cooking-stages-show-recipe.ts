import {Component, effect, inject, input} from '@angular/core';
import {
    CookingStageShowSection
} from "../../add-recipe/cooking-stage-section/cooking-stage-show-section/cooking-stage-show-section";
import CookingStageShowDTO from '../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-show.dto';
import CookingStageCreateView from '../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.view';
import ImageModelService from '../../../service/recipe_resource/image-model.service';
import {map, switchMap} from 'rxjs/operators';
import {ImageUploadService} from '../../../image_services/image-upload.service';
import {catchError, forkJoin, from, of, Subscription} from 'rxjs';

@Component({
  selector: 'app-cooking-stages-show-recipe',
    imports: [
        CookingStageShowSection
    ],
  templateUrl: './cooking-stages-show-recipe.html',
  styleUrl: './cooking-stages-show-recipe.css',
})
export class CookingStagesShowRecipe {
  private readonly imageModelService = inject(ImageModelService);
  private readonly imageUpload = inject(ImageUploadService);
  cookingStages = input<CookingStageShowDTO[]>([]);
  cookingStagesView: CookingStageCreateView[] = [];
  private stagesSub?: Subscription;

  constructor() {
    effect(() => {
      const stages = this.cookingStages();
      this.stagesSub?.unsubscribe();

      if (stages.length === 0) {
        this.cookingStagesView = [];
        return;
      }

      this.stagesSub = forkJoin(
        stages.map((stage) =>
          this.imageModelService.getImage(stage.imageId).pipe(
            switchMap((blob) => from(this.imageUpload.convertBlobToDataUrl(blob))),
            map(
              (img): CookingStageCreateView => ({
                description: stage.description,
                imageURL: img,
              }),
            ),
            catchError(() =>
              of({
                description: stage.description,
                imageURL: '',
              } as CookingStageCreateView),
            ),
          ),
        ),
      ).subscribe((data) => {
        this.cookingStagesView = data;
      });
    });
  }
}
