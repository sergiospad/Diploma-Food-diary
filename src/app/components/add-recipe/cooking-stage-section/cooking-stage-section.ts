import {Component, model} from '@angular/core';
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

  cookingStages = model<CookingStageCreateView[]>([]);

  protected readonly maxCookingStages = 30;

  protected addCookingStageToList($event: CookingStageCreateView) {
    let stages = this.cookingStages();
    stages.push($event);
    this.cookingStages.set(stages);
  }

  protected removeStage(index: number) {
    let stages = this.cookingStages();
    stages.splice(index, 1);
    this.cookingStages.set(stages);
  }

}
