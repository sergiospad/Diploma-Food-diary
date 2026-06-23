import {Component, inject, input, OnInit} from '@angular/core';
import CommentService from '../../../service/recipe_resource/comment.service';
import CommentShowDTO from '../../../DTO/entity_dto/recipe-recource/comment/comment-show.dto';
import {catchError, map, switchMap, tap} from 'rxjs/operators';
import {forkJoin, from, Observable, of} from 'rxjs';
import ImageModelService from '../../../service/recipe_resource/image-model.service';
import {ImageUploadService} from '../../../image_services/image-upload.service';
import {TokenStorageService} from '../../../security/token-storage.service';
import AvatarIndexedDb from '../../../image_services/avatar-indexed.db';
import {NotificationService} from '../../../security/notification-service';
import {CommentsAddShowRecipe} from './comments-add-show-recipe/comments-add-show-recipe';
import {DateFormatter} from '../../../util/date-formatter';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';

@Component({
  selector: 'app-comments-show-recipe',
  standalone: true,
  imports: [
    CommentsAddShowRecipe,
    DateFormatter,
    MatIcon,
    MatIconButton
  ],
  templateUrl: './comments-show-recipe.html',
  styleUrl: './comments-show-recipe.css',
})
export class CommentsShowRecipe implements OnInit {
  private readonly commentService = inject(CommentService);
  private readonly imageModelService = inject(ImageModelService);
  private readonly imageUpload = inject(ImageUploadService);
  private readonly tokenService = inject(TokenStorageService);
  private readonly avatarRep = inject(AvatarIndexedDb);
  private readonly notificationService = inject(NotificationService);
  recipeId = input<number>(-1);
  comments: CommentShowDTO[] = [];
  private readonly avatarDict = new Map<number, string>();

  ngOnInit(): void {
    this.avatarRep.getBlob()
      .then((blob) => (blob ? this.imageUpload.convertBlobToDataUrl(blob) : ''))
      .then((url) => {
        const avatarID = this.tokenService.getUser()?.avatarID;
        if (avatarID && url) {
          this.avatarDict.set(avatarID, url);
        }
      });

    this.commentService.getByRecipeID(this.recipeId()).pipe(
      switchMap((comments) =>
        comments.length
          ? forkJoin(comments.map((comment) => this.enrichComment(comment)))
          : of([] as CommentShowDTO[]),
      ),
      tap((comments) => {
        this.comments = comments;
      }),
      catchError(() => {
        this.notificationService.showSnackBar('Ошибка загрузки комментариев');
        return of([] as CommentShowDTO[]);
      }),
    ).subscribe();
  }

  private enrichComment(comment: CommentShowDTO): Observable<CommentShowDTO> {
    const commentImage$ = this.searchImage(comment.imageID);
    const userAvatar$ = this.getUserAvatar(comment.userAvatarID);

    return forkJoin([commentImage$, userAvatar$]).pipe(
      map(([image, userAvatar]) => ({
        ...comment,
        image,
        userAvatar,
      })),
    );
  }

  searchImage(id: number|undefined):Observable<string|undefined>{
    if(id)
      return this.imageModelService.getImage(id).pipe(
        switchMap((blob) => from(this.imageUpload.convertBlobToDataUrl(blob))));
    return of("");
  }

  private getUserAvatar(userAvatarID: number): Observable<string> {
    const cachedAvatar = this.avatarDict.get(userAvatarID);
    if (cachedAvatar) {
      return of(cachedAvatar);
    }
    return this.imageModelService.getImage(userAvatarID).pipe(
      switchMap((blob) => from(this.imageUpload.convertBlobToDataUrl(blob))),
      tap((url) => {
        this.avatarDict.set(userAvatarID, url);
      }),
      catchError(() => of('')),
    );
  }

  protected addToComments($event: CommentShowDTO) {
    this.comments.push($event);
  }

  protected canRemoveComment(username: string): boolean {
    const me = this.tokenService.getUser()?.username?.trim();
    const author = username?.trim();
    if (!me || !author) {
      return false;
    }
    return me === author;
  }

  protected removeComment(id: number): void {
    this.comments = this.comments.filter((c) => c.id !== id);
    this.commentService.deleteComment(id).subscribe();
  }
}
