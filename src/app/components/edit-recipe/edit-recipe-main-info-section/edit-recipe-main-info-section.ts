import {Component, inject, model} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatFormField, MatInput, MatLabel} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import RecipeShowDTO from '../../../DTO/entity_dto/recipe/recipe-show.dto';
import RecipeEditProjection from '../../../DTO/entity_dto/recipe/recipe-edit-projection';
import {ImageUploadService} from '../../../image_services/image-upload.service';

@Component({
  selector: 'app-edit-recipe-main-info-section',
    imports: [
        MatFormField,
        MatInput,
        MatLabel
    ],
  templateUrl: './edit-recipe-main-info-section.html',
  styleUrl: './edit-recipe-main-info-section.css',
})
export class EditRecipeMainInfoSection {
  recipe = model<RecipeShowDTO>({}as RecipeShowDTO);
  recipeEdit = model<RecipeEditProjection>({} as RecipeEditProjection);
  imageUpload = inject(ImageUploadService);

  protected onTitleChange(value:string) {
    this.recipe().name = value;
    this.recipeEdit().name = value;
  }

  selectImage(event:Event): void {
    const input = event.target as HTMLInputElement
    if(input.files?.[0]){
      this.recipeEdit().titleImage = input.files[0];
      this.imageUpload.convertBlobToDataUrl( input.files[0])
        .then(r => this.recipe().illustration = r)
    }
  }

  protected onSummaryChange(value:string) {
    this.recipe().summary = value;
    this.recipeEdit().summary = value;
  }
}
