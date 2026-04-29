import {Component, inject, input, output, signal} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatIcon} from '@angular/material/icon';
import {ImageUploadService} from '../../../../image_services/image-upload.service';
import CommentService from '../../../../service/recipe_resource/comment.service';
import CommentCreateDTO from '../../../../DTO/entity_dto/recipe-recource/comment/comment-create.dto';
import ImageModelService from '../../../../service/recipe_resource/image-model.service';
import {switchMap} from 'rxjs/operators';
import {Observable, of} from 'rxjs';
import CommentShowDTO from '../../../../DTO/entity_dto/recipe-recource/comment/comment-show.dto';

@Component({
  selector: 'app-comments-add-show-recipe',
  imports: [
    MatButton,
    MatFormField,
    MatIcon,
    MatInput,
    MatLabel,
  ],
  templateUrl: './comments-add-show-recipe.html',
  styleUrl: './comments-add-show-recipe.css',
})
export class CommentsAddShowRecipe {
  private readonly imageUpload = inject(ImageUploadService);
  private readonly commentService = inject(CommentService);
  private readonly imageService = inject(ImageModelService);


  protected commentImageBlob?: Blob;
  protected commentImageURL = signal("");
  protected message = signal("");
  recipeID= input<number>(-1);
  protected commentOutput = output<CommentShowDTO>()

  selectImage(event:Event): void {
    const input = event.target as HTMLInputElement
    if(input.files?.[0]){
      this.commentImageBlob = input.files[0];
      this.imageUpload.convertBlobToDataUrl(this.commentImageBlob)
        .then(r => this.commentImageURL.set(r)
        )
    }
  }

  protected submitComment() {
    const imageId$: Observable<number | undefined> = this.commentImageBlob
      ? this.imageService.uploadImage(this.commentImageBlob, 'COMMENT')
      : of(undefined);
    imageId$
      .pipe(
        switchMap((imageID) =>
          this.commentService.createComment({
            recipeID: this.recipeID(),
            imageID: imageID,
            message: this.message(),
          } as CommentCreateDTO),
        ),
      )
      .subscribe((data) => {
        data.image = this.commentImageURL();
        this.commentOutput.emit(data);
        this.message.set('');
        this.commentImageBlob = undefined;
        this.commentImageURL.set('');
      });
  }
}

