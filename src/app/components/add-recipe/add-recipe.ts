import {Component, inject, OnInit, signal} from '@angular/core';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {CookingTimeStepper} from './cooking-time-stepper';
import IngredientCreateView from '../../DTO/entity_dto/recipe-recource/ingredient/ingredient-create.view';
import {AddIngredient} from './add-ingredient/add-ingredient';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import AddProductComponent from './add-product/add-product';
import {ImageUploadService} from '../../image_services/image-upload.service';
import CookingStageCreateView from '../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.view';

@Component({
  selector: 'app-add-recipe',
  imports: [
    MatInput,
    MatFormField,
    MatLabel,
    MatButton,
    MatIcon,
    AddIngredient,
  ],
  templateUrl: './add-recipe.html',
  styleUrl: './add-recipe.css',
})
export class AddRecipeComponent implements OnInit {

  private readonly dialog = inject(MatDialog);
  private readonly imageUpload = inject(ImageUploadService);

  protected title = signal('')
  protected summary = signal('');
  protected isPrivate = signal(false);
  protected needsTime = signal(false);
  protected cookingTime = signal(30);
  protected ingredients?:IngredientCreateView[];
  protected cookingStages?: CookingStageCreateView[];
  private readonly stepper = new CookingTimeStepper(5,5);

  /** Data URL превью — в шаблоне обычный `[src]`, не `ngSrc` (NgOptimizedImage не для data: URL). */
  titleImageURL = signal("");
  titleImageBlob: Blob | undefined;

  ngOnInit(): void {
    this.ingredients = [];
  }

  selectImage(event:Event): void {
    const input = event.target as HTMLInputElement
    if(input.files?.[0]){
      this.titleImageBlob = input.files[0];
      this.imageUpload.convertBlobToDataUrl(this.titleImageBlob)
        .then(r => this.titleImageURL.set(r))
    }
  }

  protected onCookingTimeInput(event: Event): void {
    //TODO продумать добавление директивы
    const el = event.target as HTMLInputElement;
    const parsed = this.stepper.parseInput(el.value);
    if(parsed!=null) this.cookingTime.set(parsed);
  }

  protected onCookingTimeBlur(event: Event): void {
    const el = event.target as HTMLInputElement;
    this.cookingTime.set(this.stepper.snapOnBlur(el.value));
  }


  protected addToList($event: IngredientCreateView) {
    this.ingredients?.push($event);
  }

  protected openAddProductDialog() {
    const dialogAddProductConfig = new MatDialogConfig();
    dialogAddProductConfig.width = "600px";
    this.dialog.open(AddProductComponent, dialogAddProductConfig);
  }
}
