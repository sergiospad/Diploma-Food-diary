import {Component, inject, input, signal} from '@angular/core';
import {takeUntilDestroyed, toObservable} from '@angular/core/rxjs-interop';
import CookingStageShowDTO from '../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-show.dto';
import CookingStageCreateView from '../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.view';
import ImageModelService from '../../../service/recipe_resource/image-model.service';
import {map, switchMap} from 'rxjs/operators';
import {ImageUploadService} from '../../../image_services/image-upload.service';
import {catchError, forkJoin, from, of} from 'rxjs';
import {
  CookingStageShowSection
} from '../../add-recipe/cooking-stage-section/cooking-stage-show-section/cooking-stage-show-section';

@Component({
  selector: 'app-cooking-stages-show-recipe',
  imports: [
    CookingStageShowSection,
  ],
  templateUrl: './cooking-stages-show-recipe.html',
  styleUrl: './cooking-stages-show-recipe.css',
})
export class CookingStagesShowRecipe  {

  private readonly imageModelService = inject(ImageModelService);
  private readonly imageUpload = inject(ImageUploadService);
  cookingStages = input<CookingStageShowDTO[]>([]);
  protected readonly cookingStagesView = signal<CookingStageCreateView[]>([]);

  constructor(){
    toObservable(this.cookingStages)
      .pipe(
        switchMap((stages) => {
          return forkJoin(
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
          );
        }),
        takeUntilDestroyed(),
      )
      .subscribe((data) => {
        /* Отложить до следующего тика — не менять представление в том же цикле CD, что и родитель */
        setTimeout(() => this.cookingStagesView.set(data));
      });
  }
}
