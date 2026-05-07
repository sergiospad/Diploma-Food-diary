import {Component, model, output, signal} from '@angular/core';
import CookingStageCreateView from '../../../DTO/entity_dto/recipe-recource/cooking_stage/cooking-stage-create.view';
import {AddCookingStage} from './add-cooking-stage/add-cooking-stage';
import {CookingStageShowSection} from './cooking-stage-show-section/cooking-stage-show-section';

@Component({
  selector: 'app-cooking-stage-section',
  imports: [
    AddCookingStage,
    CookingStageShowSection,
  ],
  templateUrl: './cooking-stage-section.html',
  styleUrl: './cooking-stage-section.css',
})
export class CookingStageSection {

  cookingStages = signal<CookingStageCreateView[]>([]);
  toMainComponentStages = output<CookingStageCreateView[]>();

  protected readonly maxCookingStages = 30;

  protected addCookingStageToList($event: CookingStageCreateView) {
    this.cookingStages.update(list=> ([...list, $event]))
    this.toMainComponentStages.emit(this.cookingStages());
  }

  protected removeStage(index: number) {
    this.cookingStages.update(list=> (list.splice(index, 1)));
    this.toMainComponentStages.emit(this.cookingStages());
  }

}
