import {Component, inject, output, signal} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatIcon} from '@angular/material/icon';
import {ImageUploadService} from '../../../image_services/image-upload.service';

@Component({
  selector: 'app-recipe-main-info-section',
  imports: [
    MatButton,
    MatFormField,
    MatIcon,
    MatInput,
    MatLabel
  ],
  templateUrl: './recipe-main-info-section.html',
  styleUrl: './recipe-main-info-section.css',
})
export class RecipeMainInfoSection {

  private readonly imageUpload = inject(ImageUploadService);

  protected title = signal('')
  protected toMainComponentTitle = output<string>()

  protected summary = signal('');
  protected toMainComponentSummary = output<string>()

  protected titleImageURL = signal("");

  protected titleImageBlob: Blob | undefined;
  protected toMainComponentTitleImageBlob = output<Blob>();

  onTitleChange(changeTitle: string) {
    this.title.set(changeTitle);
    this.toMainComponentTitle.emit(changeTitle);
  }

  onSummaryChange(changeSummary: string) {
    this.summary.set(changeSummary);
    this.toMainComponentSummary.emit(changeSummary);
  }

  selectImage(event:Event): void {
    const input = event.target as HTMLInputElement
    if(input.files?.[0]){
      this.titleImageBlob = input.files[0];
      this.toMainComponentTitleImageBlob.emit(this.titleImageBlob);
      this.imageUpload.convertBlobToDataUrl(this.titleImageBlob)
        .then(r => this.titleImageURL.set(r)
        )
    }
  }

}
