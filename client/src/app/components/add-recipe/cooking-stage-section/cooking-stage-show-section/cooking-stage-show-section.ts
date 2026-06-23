import {Component, input, model} from '@angular/core';
import CookingStageCreateView from '../../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.view';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';

@Component({
  selector: 'app-cooking-stage-show-section',
  imports: [
    MatIcon,
    MatIconButton
  ],
  templateUrl: './cooking-stage-show-section.html',
  styleUrl: './cooking-stage-show-section.css',
})
export class CookingStageShowSection {

  cookingStages = model<CookingStageCreateView[]>([]);

  canRemove = input<boolean>(false);

  removeStage(index: number): void {
    this.cookingStages.update((list) => list.filter((_, i) => i !== index));
  }
}
