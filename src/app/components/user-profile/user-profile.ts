import {Component, inject, OnInit, signal} from '@angular/core';
import {TokenStorageService} from '../../security/token-storage.service';
import {UserService} from '../../service/user.service';
import AvatarIndexedDb from '../../image_services/avatar-indexed.db';
import UserEditDto from '../../DTO/entity_dto/user/user-edit.dto';
import {catchError, switchMap, tap} from 'rxjs/operators';
import {EMPTY, from, of} from 'rxjs';
import ImageModelService from '../../service/recipe_resource/image-model.service';
import {ImageUploadService} from '../../image_services/image-upload.service';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatRadioButton, MatRadioGroup} from '@angular/material/radio';
import {NotificationService} from '../../security/notification-service';
import {httpErrorMessage} from '../../util/error.service';
import UserProfileValidator from './profile.validator';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {ChangePassword} from './change-password/change-password';
import AddProductComponent from '../add-recipe/recipe-main-info-ingredients-section/add-product/add-product';

@Component({
  selector: 'app-user-profile',
  imports: [
    FormsModule,
    MatButton,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatRadioGroup,
    MatRadioButton
  ],
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.css',
})
export class UserProfile implements OnInit {
  private readonly dialog = inject(MatDialog);
  private readonly userService = inject(UserService);
  private readonly avatarRep = inject(AvatarIndexedDb);
  private readonly imageUpload = inject(ImageUploadService);
  private readonly imageService = inject(ImageModelService);
  private readonly tokenService = inject(TokenStorageService);
  private readonly formBuilder = inject(FormBuilder);
  private readonly notificationService = inject(NotificationService);

  user: UserEditDto = {}as UserEditDto;
  avatar = signal<string>("");
  avatarBlob = signal<Blob | null>(null);

  protected userForm!: FormGroup;

  ngOnInit(): void {
    this.userService
      .getUserEditDTO()
      .pipe(
        tap((user) => {
          this.user = user;
          this.userForm = this.createForm();
        }),
        switchMap(() => from(this.avatarRep.getBlob())),
        switchMap((blob) => from(this.imageUpload.convertBlobToDataUrl(blob))),
      )
      .subscribe((url) => this.avatar.set(url));
  }

  createForm(): FormGroup {
    return this.formBuilder.group({
      username: [this.user.username, Validators.compose([Validators.required])],
      email: [
        this.user.email,
        Validators.compose([Validators.required, Validators.email]),
      ],

      gender: [this.user.gender ?? 'M'],
      birthdate: [this.user.birthDate, UserProfileValidator.birthdateValidator],
      height: [this.user.height ?? null, UserProfileValidator.heightCmValidator],
    });
  }

  protected selectImage($event: Event) {
    const input = $event.target as HTMLInputElement
    if(input.files?.[0]){
      this.avatarBlob.set(input.files[0]);
      this.imageUpload.convertBlobToDataUrl(this.avatarBlob()!)
        .then(r => this.avatar.set(r))
    }
  }

  protected updateUser() {
    const avatarID = this.tokenService.getUser()!.avatarID;
    this.user.username = this.userForm.controls['username'].value;
    this.user.email = this.userForm.controls['email'].value;
    this.user.gender = this.userForm.controls['gender'].value;
    this.user.birthDate = this.userForm.controls['birthdate'].value;
    this.user.height = this.userForm.controls['height'].value;

    this.userService
      .updateUser(this.user)
      .pipe(
        switchMap(() => {
          const blob = this.avatarBlob();
          if (blob != null && blob.size > 0) {
            return this.imageService.updateImage(blob, avatarID).pipe(
              switchMap(() => from(this.avatarRep.saveBlob(blob))),
            );
          }
          return of(null);
        }),
        tap(() => {
          this.notificationService.showSnackBar('Данные обновлены');
          this.avatarBlob.set(null);
        }),
        catchError((err: unknown) => {
          this.notificationService.showSnackBar(
            httpErrorMessage(err, 'Не удалось сохранить'),
          );
          return EMPTY;
        }),
      )
      .subscribe();
  }



  protected onChangePassword() {
    const dialogAddProductConfig = new MatDialogConfig();
    dialogAddProductConfig.width = "600px";
    this.dialog.open(ChangePassword, dialogAddProductConfig);
  }
}
