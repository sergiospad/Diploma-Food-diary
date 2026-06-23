import {Component, inject, output, signal} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {ImageUploadService} from '../../../../image_services/image-upload.service';
import CookingStageCreateView from '../../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.view';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {submit} from '@angular/forms/signals';

@Component({
  selector: 'app-add-cooking-stage',
  imports: [
    MatButton,
    MatIcon,
    MatFormField,
    MatInput,
    MatLabel
  ],
  templateUrl: './add-cooking-stage.html',
  styleUrl: './add-cooking-stage.css',
})
export class AddCookingStage {

  imageUpload = inject(ImageUploadService);
  showIllustrationIRL = signal("");
  illustrationBLob?:Blob;
  description= signal("");
  cookingStage = output<CookingStageCreateView>();


  protected selectImage(event: Event) {
    const input = event.target as HTMLInputElement
    if(input.files?.[0]){
      this.illustrationBLob = input.files[0];
      this.imageUpload.convertBlobToDataUrl(this.illustrationBLob)
        .then(r => this.showIllustrationIRL.set(r))
    }
  }

  protected addToRecipe(){
    const stage = this.makeCookingStage();
    this.cookingStage.emit(stage);
    this.description = signal("");
    this.showIllustrationIRL = signal("");
    this.illustrationBLob = undefined;
  }

  makeCookingStage(){
    return  {
      description:this.description().toString(),
      imageBlob:this.illustrationBLob,
      imageURL: this.showIllustrationIRL().toString()===''?null:this.showIllustrationIRL().toString(),
    }as CookingStageCreateView;
  }

  protected readonly submit = submit;
}
